package com.employee.employee.service;

import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.entity.Employee;

import java.util.List;
import java.util.UUID;

public interface ServiceEmployee {

    EmployeeDto getEmployeeById(UUID id);

    Employee saveEmployee(Employee employee);

    String statusUser(Employee employee, Status status);

    List<Employee> getEmployeesByRole(Role role);

    List<Employee> getEmployeesByDepartmentId(UUID idDepartment);

    List<Employee> getEmployeesByManagerId(UUID idDepartment);
}
