package com.employee.employee.service;

import com.employee.employee.constant.Role;
import com.employee.employee.entity.Employee;

import java.util.List;
import java.util.UUID;

public interface ServiceEmployee {

    Employee getEmployeeById(UUID id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    String disableUser(UUID id);

    List<Employee> getEmployeesByRole(Role role);

    List<Employee> getEmployeesByDepartmentId(UUID idDepartment);

    List<Employee> getEmployeesByManagerId(UUID idDepartment);
}
