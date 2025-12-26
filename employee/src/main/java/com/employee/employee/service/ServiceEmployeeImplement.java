package com.employee.employee.service;

import com.employee.employee.constant.Role;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceEmployeeImplement implements ServiceEmployee{

    @Override
    public EmployeeDto getEmployeeById(UUID id) {
        return null;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeRequest employee) {
        return null;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeRequest employee) {
        return null;
    }

    @Override
    public String disableUser(UUID id) {
        return "";
    }

    @Override
    public List<EmployeeDto> getEmployeesByRole(Role role) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartmentId(UUID idDepartment) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getEmployeesByManagerId(UUID idDepartment) {
        return List.of();
    }
}
