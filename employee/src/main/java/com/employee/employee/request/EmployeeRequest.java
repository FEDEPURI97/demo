package com.employee.employee.request;

import com.employee.employee.constant.Level;
import com.employee.employee.constant.Role;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(

        String name,

        String lastName,

        String email,

        String phoneNumber,

        LocalDate dateOfBirth,

        Role role,

        Level level,

        BigDecimal salary

) {
}
