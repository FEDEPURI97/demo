package com.employee.employee.dto;

import com.employee.employee.constant.Level;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record EmployeeDto(

        String name,

        String lastName,

        String email,

        String phoneNumber,

        LocalDate dateOfBirth,

        String employeeCode,

        DepartmentDto departmentDto,

        Role role,

        Level level,

        ManagerDto managerDto,

        LocalDate hireDate,

        LocalDate endDate,

        Status status,

        BigDecimal salary,

        List<ProjectDto> projectsDto

) {
}
