package com.employee.employee.service;

import com.employee.employee.constant.Role;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;

import java.util.List;
import java.util.UUID;

public interface ServiceEmployee {

    EmployeeDto getEmployeeById(UUID id);

    EmployeeDto createEmployee(EmployeeRequest employee);

    EmployeeDto updateEmployee(EmployeeRequest employee);

    String disableUser(UUID id);

    List<EmployeeDto> getEmployeesByRole(Role role);

    List<EmployeeDto> getEmployeesByDepartmentId(UUID idDepartment);

    List<EmployeeDto> getEmployeesByManagerId(UUID idDepartment);
}
