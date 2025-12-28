package com.project.project.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProjectsRequest(

        @NotBlank(message = "Nome non può essere vuoto")
        String name,
        @NotBlank(message = "Description non può essere vuoto")
        String description,
        @NotNull(message = "Salary non può essere nulla")
        @DecimalMin(value = "0.0", inclusive = false, message = "Salary deve essere maggiore di 0")
        BigDecimal budget

) {
}
