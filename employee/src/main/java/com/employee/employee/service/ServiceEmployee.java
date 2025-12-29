package com.employee.employee.service;

import com.employee.employee.constant.StatusEmployee;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ServiceEmployee {

    Mono<EmployeeDto> getEmployeeById(Integer id);

    Mono<EmployeeDto> createEmployee(EmployeeRequest employee);

    Mono<String> updateStatus(Integer employeeId, StatusEmployee statusEmployee);

    Mono<String> updateSalary(Integer userId, BigDecimal salary);

    Flux<EmployeeDto> getAllEmploye();

    Mono<String> deleteEmployee(Integer id);
}
