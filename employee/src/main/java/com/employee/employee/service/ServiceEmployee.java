package com.employee.employee.service;

import com.employee.employee.constant.Role;
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

    Mono<String> statusUser(UUID employeeId, Status status);

    Flux<EmployeeDto> getEmployeesByRole(Role role);

    Flux<EmployeeDto> getEmployeesByDepartmentId(UUID idDepartment);

    Flux<EmployeeDto> getEmployeesByManagerId(UUID idDepartment);

    Mono<String> addProjectToEmployee(UUID userId, UUID projectId);

    Mono<String> updateRole(UUID userId, Role newRole);

    Mono<String> salaryUser(UUID userId, BigDecimal salary);

}
