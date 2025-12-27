package com.employee.employee.service;

import com.employee.employee.client.DepartmentClient;
import com.employee.employee.client.ProjectClient;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.DepartmentDto;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.ProjectDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.exception.EntityNotIdException;
import com.employee.employee.factory.EmployeeMapper;
import com.employee.employee.repository.EmployeRepository;
import com.employee.employee.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ServiceEmployeeImpl implements ServiceEmployee{

    private final EmployeRepository repository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentClient departmentClient;
    private final ProjectClient projectClient;
    private final Util util;

    public ServiceEmployeeImpl(EmployeRepository repository, EmployeeMapper employeeMapper, DepartmentClient departmentClient, ProjectClient projectClient, Util util) {
        this.repository = repository;
        this.employeeMapper = employeeMapper;
        this.departmentClient = departmentClient;
        this.projectClient = projectClient;
        this.util = util;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<EmployeeDto> getEmployeeById(UUID id) {

        Mono<Employee> modelMono = repository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotIdException("")));

        Mono<DepartmentDto> departmentDtoMono =  modelMono.flatMap(model ->
                departmentClient.getDepartmentById(model.getDepartmentId())
        );
        Flux<UUID> uuid = modelMono.flatMapMany(model ->
                Flux.fromIterable(util.filterForActualProject(model.getProjectHistory()))
        );

        Flux<ProjectDto> projectDtoFlux = uuid.flatMap(projectClient::getProjectById);

        Mono<BigDecimal> salaryMono = modelMono.map(model->
                util.getActualSalary(model.getSalaryHistory())
                );

        return Mono.zip(modelMono, salaryMono, projectDtoFlux.collectList(), departmentDtoMono)
                .map(tuple ->
                        employeeMapper.toDto(tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4())
                );
    }

    @Override
    @Transactional
    public Mono<Employee> saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    @Transactional
    public Mono<String> statusUser(Employee employee , Status status) {
        employee.setStatus(status);
        repository.save(employee);
        return Mono.just(
                String.format("Modifica status in: %s, dello user: %s %s avvenuto con successo",
                        status, employee.getName(), employee.getLastName())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Employee> getEmployeesByRole(Role role) {
        return repository.findByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Employee> getEmployeesByDepartmentId(UUID idDepartment) {
        return repository.findByDepartmentId(idDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Employee> getEmployeesByManagerId(UUID idManager) {
        return repository.findByManagerId(idManager);
    }
}
