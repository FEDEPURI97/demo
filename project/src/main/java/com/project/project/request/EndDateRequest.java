package com.project.project.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record EndDateRequest(

        @NotNull
        UUID id,
        @NotNull
        LocalDate date

) {
}
