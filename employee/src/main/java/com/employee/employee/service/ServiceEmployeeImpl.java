package com.employee.employee.service;

import com.employee.employee.client.Client;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.DepartmentDto;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.ProjectDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.exception.EntityNotIdException;
import com.employee.employee.factory.EmployeeMapper;
import com.employee.employee.repository.EmployeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceEmployeeImpl implements ServiceEmployee{

    private final EmployeRepository repository;
    private final EmployeeMapper employeeMapper;
    private final Client client;

    public ServiceEmployeeImpl(EmployeRepository repository, EmployeeMapper employeeMapper, Client client) {
        this.repository = repository;
        this.employeeMapper = employeeMapper;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<EmployeeDto> getEmployeeById(UUID id) {

        Mono<Employee> modelMono = repository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotIdException("")));

        Mono<DepartmentDto> departmentDtoMono =  modelMono.flatMap(model ->
                client.getDepartmentById(model.getDepartmentId())
        );

        Mono<List<ProjectDto>> projectDtoMono = modelMono.flatMap(model -> {
            List<UUID> projects = model.getProject() != null ? model.getProject() : Collections.emptyList();
            return client.getProjectsByIds(projects);
        });

        return Mono.zip(modelMono, projectDtoMono, departmentDtoMono)
                .map(tuple ->
                        employeeMapper.toDto(tuple.getT1(), tuple.getT2(), tuple.getT3())
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
