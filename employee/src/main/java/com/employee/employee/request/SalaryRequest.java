package com.employee.employee.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.UUID;

public record SalaryRequest(

        @PathVariable("idUser")@NotNull UUID idUser,

        @NotNull(message = "Salary non può essere nulla")
        @DecimalMin(value = "0.0", inclusive = false, message = "Salary deve essere maggiore di 0")
        BigDecimal salary

) {}
