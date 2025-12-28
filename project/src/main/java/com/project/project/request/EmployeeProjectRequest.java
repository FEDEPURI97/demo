package com.project.project.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmployeeProjectRequest(

        @NotNull
        UUID idUser,

        @NotNull
        UUID idProject

) {
}
