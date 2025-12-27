package com.employee.employee.service;

import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.entity.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ServiceEmployee {

    Mono<EmployeeDto> getEmployeeById(UUID id);

    Mono<Employee> saveEmployee(Employee employee);

    Mono<String> statusUser(Employee employee, Status status);

    Flux<Employee> getEmployeesByRole(Role role);

    Flux<Employee> getEmployeesByDepartmentId(UUID idDepartment);

    Flux<Employee> getEmployeesByManagerId(UUID idDepartment);
}
