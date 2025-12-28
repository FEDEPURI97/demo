package com.employee.employee.service;

import com.employee.employee.constant.Status;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.exception.DuplicateCustomException;
import com.employee.employee.exception.EntityNotIdException;
import com.employee.employee.factory.EmployeeMapper;
import com.employee.employee.repository.EmployeRepository;
import com.employee.employee.request.EmployeeRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ServiceEmployeeImpl implements ServiceEmployee{

    private final EmployeRepository repositoryEmployee;
    private final EmployeeMapper employeeMapper;

    public ServiceEmployeeImpl(EmployeRepository repositoryEmployee, EmployeeMapper employeeMapper) {
        this.repositoryEmployee = repositoryEmployee;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<EmployeeDto> getEmployeeById(UUID id) {
        return repositoryEmployee.findById(id)
                .map(employeeMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityNotIdException(id)));
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
        return model.map(employeeMapper::toDto);
    }

    @Transactional
    public Mono<String> updateStatus(UUID userId , Status status) {
        return repositoryEmployee.updateStatus(userId, status.name())
                .flatMap(updated -> {
                    if (updated == 0) {
                        return Mono.error(new EntityNotIdException(userId));
                    }
                    return Mono.just("Status aggiornato con successo");
                });
    }

    @Transactional
    public Mono<String> updateSalary(UUID userId, BigDecimal salary) {
        return repositoryEmployee.updateSalary(userId, salary)
                .flatMap(updated -> {
                    if (updated == 0) {
                        return Mono.error(new EntityNotIdException(userId));
                    }
                    return Mono.just("Salario aggiornato con successo");
                });
    }

    @Override
    public Flux<EmployeeDto> getAllEmploye() {
        return repositoryEmployee.findAll()
                .map(employeeMapper::toDto);
    }

    @Override
    @Transactional
    public Mono<String> deleteEmployee(UUID id) {
        return repositoryEmployee.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotIdException(id)))
                .flatMap(employee ->
                        repositoryEmployee.delete(employee)
                                .then(Mono.just("Employee eliminato con successo"))
                );
    }


}
