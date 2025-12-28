package com.employee.employee.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(

        @NotBlank(message = "Codice fiscale non può essere vuoto")
        @Size(min = 16, max = 16, message = "Codice fiscale deve essere lungo 16 caratteri")
        String fiscalCode,
        @NotBlank(message = "Nome non può essere vuoto")
        String name,
        @NotBlank(message = "Cognome non può essere vuoto")
        String lastName,
        @Email(message = "Email non valida")
        @NotBlank(message = "Email non può essere vuota")
        String email,
        @NotBlank(message = "Numero di telefono non può essere vuoto")
        @Pattern(regexp = "\\+?[0-9]{7,15}", message = "Numero di telefono non valido")
        String phoneNumber,
        @NotNull(message = "Data di nascita non può essere nulla")
        @Past(message = "La data di nascita deve essere nel passato")
        LocalDate dateOfBirth,
        @NotNull(message = "Salary non può essere nulla")
        @DecimalMin(value = "0.0", inclusive = false, message = "Salary deve essere maggiore di 0")
        BigDecimal salary
) {
}
