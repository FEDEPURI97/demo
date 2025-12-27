package com.employee.employee.service;

import com.employee.employee.client.Client;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.DepartmentDto;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.ManagerDto;
import com.employee.employee.dto.ProjectDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.entity.ProjectHistory;
import com.employee.employee.entity.RoleHistory;
import com.employee.employee.entity.SalaryHistory;
import com.employee.employee.exception.DuplicateCustomException;
import com.employee.employee.exception.EntityNotIdException;
import com.employee.employee.factory.EmployeeMapper;
import com.employee.employee.repository.EmployeRepository;
import com.employee.employee.repository.ProjectRepository;
import com.employee.employee.repository.RoleHistoryRepository;
import com.employee.employee.repository.SalaryRepository;
import com.employee.employee.request.EmployeeRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceEmployeeImpl implements ServiceEmployee{

    private final EmployeRepository repositoryEmployee;
    private final SalaryRepository repositorySalary;
    private final ProjectRepository repositoryProject;
    private final RoleHistoryRepository repositoryRoleHistory;
    private final EmployeeMapper employeeMapper;
    private final Client client;

    public ServiceEmployeeImpl(EmployeRepository repositoryEmployee, SalaryRepository repositorySalary, ProjectRepository repositoryProject, RoleHistoryRepository repositoryRoleHistory, EmployeeMapper employeeMapper, Client client) {
        this.repositoryEmployee = repositoryEmployee;
        this.repositorySalary = repositorySalary;
        this.repositoryProject = repositoryProject;
        this.repositoryRoleHistory = repositoryRoleHistory;
        this.employeeMapper = employeeMapper;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<EmployeeDto> getEmployeeById(UUID id) {

        return repositoryEmployee.findById(id)
                .switchIfEmpty(Mono.error(
                        new EntityNotIdException(
                                String.format("L'utente con il seguente id: %s non è stato trovato", id)
                        )
                ))
                .flatMap(this::buildEmployeeDto);
    }

    @Override
    @Transactional
    public Mono<EmployeeDto> createEmployee(EmployeeRequest request) {
        Employee employee = employeeMapper.toModelFromRequest(request);
        employee.setId(UUID.randomUUID());
        employee.setEmployeeCode(request.fiscalCode());
        employee.setStatus(Status.SUSPENDED);
        employee.setHireDate(LocalDate.now());
        Mono<Employee> model = repositoryEmployee.save(employee)
                .onErrorMap(e -> {
                    if (e instanceof DuplicateKeyException || e instanceof DataIntegrityViolationException) {
                        return new DuplicateCustomException("Email o codice fiscale già presente");
                    }
                    return e;
                });
        return model.map(employeeMapper::toDtoCreate);
    }

    @Override
    @Transactional
    public Mono<String> statusUser(UUID userId , Status status) {
        return repositoryEmployee.updateStatus(userId, status.name())
                .flatMap(updated -> {
                    if (updated == 0) {
                        return Mono.error(new EntityNotIdException(
                                String.format("L'utente con il seguente id: %s non è stato trovato", userId)
                        ));
                    }
                    return Mono.just("Status aggiornato con successo");
                });
    }

    @Override
    @Transactional
    public Mono<String> salaryUser(UUID userId, BigDecimal salary) {

        return repositoryEmployee.findById(userId)
                .switchIfEmpty(Mono.error(new EntityNotIdException(
                        String.format("L'utente con il seguente id: %s non è stato trovato", userId)))
                )
                .flatMap(employee -> {
                    employee.setSalary(salary);
                    Mono<Employee> updateEmployee = repositoryEmployee.save(employee);
                    Mono<Void> closeCurrentSalary =
                            repositorySalary.findByEmployeeId(userId)
                                    .filter(sh -> sh.getEndDate() == null)
                                    .next()
                                    .flatMap(sh -> {
                                        sh.setEndDate(LocalDate.now());
                                        return repositorySalary.save(sh).then();
                                    })
                                    .switchIfEmpty(Mono.empty());

                    Mono<SalaryHistory> createNewSalary =
                            Mono.fromSupplier(() -> {
                                SalaryHistory sh = new SalaryHistory();
                                sh.setEmployeeId(userId);
                                sh.setRole(Role.valueOf(employee.getRole()));
                                sh.setStartDate(LocalDate.now());
                                sh.setSalary(salary);
                                return sh;
                            }).flatMap(repositorySalary::save);

                    return closeCurrentSalary
                            .then(updateEmployee)
                            .then(createNewSalary)
                            .thenReturn("Salario aggiornato con successo");
                });
    }




    @Override
    @Transactional
    public Mono<String> addProjectToEmployee(UUID userId, UUID projectId) {
        return repositoryEmployee.findById(userId)
                .switchIfEmpty(Mono.error(new EntityNotIdException(
                                String.format("L'utente con il seguente id: %s non è stato trovato", userId)
                        )))
                .flatMap(employee ->
                        client.getProjectById(projectId)
                                .switchIfEmpty(Mono.error(new EntityNotIdException(
                                        "Progetto con id " + projectId + " non trovato"
                                )))
                                .flatMap(projectDto -> {
                                    ProjectHistory projectHistory = new ProjectHistory();
                                    projectHistory.setProjectId(projectId);
                                    projectHistory.setEmployeeId(userId);
                                    projectHistory.setStartDate(LocalDate.now());
                                    projectHistory.setRole(Role.valueOf(employee.getRole()));

                                    return repositoryProject.save(projectHistory)
                                            .thenReturn("Progetto aggiunto con successo");
                                })
                );
    }

    @Override
    @Transactional
    public Mono<String> updateRole(UUID userId, Role newRole) {

        return repositoryEmployee.findById(userId)
                .switchIfEmpty(Mono.error(new EntityNotIdException(
                                String.format("L'utente con il seguente id: %s non è stato trovato", userId)
                        )))
                .flatMap(employee -> {
                    Mono<Void> closeCurrentRoleHistory =
                            repositoryRoleHistory.findByEmployeeId(userId)
                                    .filter(rh -> rh.getEndDate() == null)
                                    .next()
                                    .flatMap(rh -> {
                                        rh.setEndDate(LocalDate.now());
                                        return repositoryRoleHistory.save(rh).then();
                                    })
                                    .switchIfEmpty(Mono.empty());

                    Mono<RoleHistory> createNewRoleHistory =
                            Mono.fromSupplier(() -> {
                                RoleHistory rh = new RoleHistory();
                                rh.setId(UUID.randomUUID());
                                rh.setRole(newRole);
                                rh.setStartDate(LocalDate.now());
                                rh.setEmployeeId(userId);
                                return rh;
                            }).flatMap(repositoryRoleHistory::save);

                    employee.setRole(Role.valueOf(newRole.name()));
                    Mono<Employee> updateEmployeeRole = repositoryEmployee.save(employee);

                    return closeCurrentRoleHistory
                            .then(updateEmployeeRole)
                            .then(createNewRoleHistory)
                            .thenReturn("Ruolo aggiornato con successo");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<EmployeeDto> getEmployeesByRole(Role role) {
        return repositoryEmployee.findByRole(role)
                .flatMap(this::buildEmployeeDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<EmployeeDto> getEmployeesByDepartmentId(UUID idDepartment) {
        Mono<DepartmentDto> departmentMono = client.getDepartmentById(idDepartment).cache();
        return repositoryEmployee.findByDepartmentId(idDepartment)
                .flatMap(employee ->
                        departmentMono.flatMap(department ->
                                buildEmployeeDtoForDepartment(employee, department)
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<EmployeeDto> getEmployeesByManagerId(UUID idManager) {
        Mono<ManagerDto> managerDtoMono = repositoryEmployee.findById(idManager)
                .map(employeeMapper::toManagerDto);
        return repositoryEmployee.findByManagerId(idManager)
                .flatMap(employee ->
                        managerDtoMono.flatMap(managerDto ->
                                buildEmployeeDtoForManager(employee, managerDto)
                        )
                );
    }

    private Mono<EmployeeDto> buildEmployeeDto(Employee employee) {

        Mono<DepartmentDto> departmentMono = client.getDepartmentById(employee.getDepartmentId());
        Mono<List<ProjectDto>> projectMono = retriveProject(employee.getId());
        Mono<ManagerDto> managerDtoMono = repositoryEmployee.findById(employee.getManagerId())
                .map(employeeMapper::toManagerDto);
        return Mono.zip(departmentMono, projectMono, managerDtoMono)
                .map(mono ->
                        employeeMapper.toDto(employee, mono.getT2(), mono.getT1(), mono.getT3())
                );

    }

    private Mono<EmployeeDto> buildEmployeeDtoForDepartment(Employee employee, DepartmentDto departmentDto) {
        Mono<List<ProjectDto>> projectMono = retriveProject(employee.getId());
        Mono<ManagerDto> managerDtoMono = repositoryEmployee.findById(employee.getManagerId())
                .map(employeeMapper::toManagerDto);
        return Mono.zip(projectMono, managerDtoMono)
                .map(mono ->
                        employeeMapper.toDto(employee, mono.getT1(), departmentDto, mono.getT2())
                );

    }

    private Mono<EmployeeDto> buildEmployeeDtoForManager(Employee employee, ManagerDto managerDto) {
        Mono<DepartmentDto> departmentMono = client.getDepartmentById(employee.getDepartmentId());
        Mono<List<ProjectDto>> projectMono = retriveProject(employee.getId());
        return Mono.zip(departmentMono, projectMono)
                .map(mono ->
                        employeeMapper.toDto(employee, mono.getT2(), mono.getT1(), managerDto)
                );

    }

    private Mono<List<ProjectDto>> retriveProject(UUID employeeId){
        Flux<ProjectHistory> projectHistoryFlux = repositoryProject.findByEmployeeId(employeeId);
        Mono<List<UUID>> projectIdsMono = projectHistoryFlux
                .map(ProjectHistory::getProjectId)
                .collectList()
                .defaultIfEmpty(Collections.emptyList());
        return  projectIdsMono.flatMap(client::getProjectsById);

    }
}
