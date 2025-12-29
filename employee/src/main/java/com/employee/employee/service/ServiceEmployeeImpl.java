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
                .flatMap(savedEmployee -> eventProducer.sendUserRegisteredEvent(
                        employeeMapper.toRegisteredDto(employee,
                                "https://tuosito.com/activate/" + savedEmployee.getId()))
                        .thenReturn(employeeMapper.toDto(savedEmployee)));
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
        return repositoryEmployee.updateSalary(userId, salary)
                .flatMap(updated -> {
                    if (updated == 0) {
                        return Mono.error(new EmployeeNotIdException(userId));
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
    public Mono<String> deleteEmployee(Integer id) {
        return repositoryEmployee.findById(id)
                .switchIfEmpty(Mono.error(new EmployeeNotIdException(id)))
                .flatMap(employee ->
                        repositoryEmployee.delete(employee)
                                .then(Mono.just("Employee eliminato con successo"))
                );
    }


}
