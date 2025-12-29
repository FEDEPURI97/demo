package com.employee.employee.service;

import com.employee.employee.constant.StatusEmployee;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.event.EmployeeEventProducer;
import com.employee.employee.exception.DuplicateCustomException;
import com.employee.employee.exception.EmployeeNotIdException;
import com.employee.employee.factory.EmployeeMapper;
import com.employee.employee.repository.EmployeRepository;
import com.employee.employee.request.EmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ServiceEmployeeImpl implements ServiceEmployee{

    private final EmployeRepository repositoryEmployee;
    private final EmployeeMapper employeeMapper;
    private final EmployeeEventProducer eventProducer;
    @Value("${app.activation.url}")
    private String activationUrl;

    @Override
    public Mono<EmployeeDto> getEmployeeById(Integer id) {
        return repositoryEmployee.findById(id)
                .map(employeeMapper::toDto)
                .switchIfEmpty(Mono.error(new EmployeeNotIdException(id)));
    }

    @Override
    public Mono<EmployeeDto> createEmployee(EmployeeRequest request) {
        Employee employee = employeeMapper.toModelFromRequest(request);
        employee.setEmployeeCode(request.fiscalCode());
        employee.setStatus(StatusEmployee.SUSPENDED);
        employee.setHireDate(LocalDate.now());
        return repositoryEmployee.save(employee)
                .onErrorMap(e -> {
                    if (e instanceof DuplicateKeyException || e instanceof DataIntegrityViolationException) {
                        return new DuplicateCustomException("Email o codice fiscale già presente");
                    }
                    return e;
                })
                .flatMap(employeeDb -> eventProducer.sendUserRegisteredEvent(
                        employeeMapper.toRegisteredDto(employeeDb,
                                activationUrl.concat(String.valueOf(employeeDb.getId()))))
                        .thenReturn(employeeMapper.toDto(employeeDb)));
    }



    @Override
    public Mono<String> updateStatus(Integer userId , StatusEmployee statusEmployee) {
        return repositoryEmployee.findById(userId)
                .flatMap(employee -> {
                    employee.setStatus(statusEmployee);
                    if (statusEmployee.equals(StatusEmployee.TERMINATED)) {
                        employee.setEndDate(LocalDate.now());
                    }
                    repositoryEmployee.save(employee);
                    return Mono.just("Status aggiornato con successo");
                }).switchIfEmpty(Mono.error(new EmployeeNotIdException(userId)));
    }

    @Override
    public Mono<String> updateSalary(Integer userId, BigDecimal salary) {
        return repositoryEmployee.findById(userId)
                .flatMap(employee -> {
                    employee.setSalary(salary);
                    repositoryEmployee.save(employee);
                    return Mono.just("Salario aggiornato con successo");
                }).switchIfEmpty(Mono.error(new EmployeeNotIdException(userId)));
    }

    @Override
    public Flux<EmployeeDto> getAllEmploye() {
        return repositoryEmployee.findAll()
                .map(employeeMapper::toDto);
    }

    @Override
    public Mono<String> deleteEmployee(Integer id) {
        return repositoryEmployee.findById(id)
                .switchIfEmpty(Mono.error(new EmployeeNotIdException(id)))
                .flatMap(employee ->
                        repositoryEmployee.delete(employee)
                                .then(Mono.just("Employee eliminato con successo"))
                );
    }


}
