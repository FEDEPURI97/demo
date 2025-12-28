package com.employee.employee.dto;

import com.employee.employee.constant.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeDto(

        String name,

        String lastName,

        String email,

        String phoneNumber,

        LocalDate dateOfBirth,

        String employeeCode,

        LocalDate hireDate,

        LocalDate endDate,

        Status status,

        BigDecimal salary

) {
}
