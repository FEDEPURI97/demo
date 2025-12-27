package com.employee.employee.request;

import com.employee.employee.constant.Level;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeRequest(

        String fiscalCode,

        String name,

        String lastName,

        String email,

        String phoneNumber,

        LocalDate dateOfBirth,

        Role role,

        Level level,

        Status status,

        BigDecimal salary,

        UUID managerId
) {
}
