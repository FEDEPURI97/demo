package com.employee.employee.service;

import com.employee.employee.constant.Status;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public interface ServiceEmployee {

    Mono<EmployeeDto> getEmployeeById(UUID id);

    Mono<EmployeeDto> createEmployee(EmployeeRequest employee);

    Mono<String> updateStatus(UUID employeeId, Status status);

    Mono<String> updateSalary(UUID userId, BigDecimal salary);

    Flux<EmployeeDto> getAllEmploye();

    Mono<String> deleteEmployee(UUID id);
}
