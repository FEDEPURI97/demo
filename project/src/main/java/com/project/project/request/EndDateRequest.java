package com.project.project.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EndDateRequest(

        @NotNull
        Integer id,
        @NotNull
        LocalDate date

) {
}
