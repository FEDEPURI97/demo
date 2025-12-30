package com.project.project.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BudgetRequest(

        @NotNull
        Integer id,

        @NotNull(message = "Salary non può essere nulla")
        @DecimalMin(value = "0.0", inclusive = false, message = "Salary deve essere maggiore di 0")
        BigDecimal budget

) {}
