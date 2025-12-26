package com.employee.employee.service;

import com.employee.employee.constant.Role;
import com.employee.employee.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceEmployeeImplement implements ServiceEmployee{
    @Override
    public Employee getEmployeeById(UUID id) {
        return null;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public String disableUser(UUID id) {
        return "";
    }

    @Override
    public List<Employee> getEmployeesByRole(Role role) {
        return List.of();
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(UUID idDepartment) {
        return List.of();
    }

    @Override
    public List<Employee> getEmployeesByManagerId(UUID idDepartment) {
        return List.of();
    }
}
