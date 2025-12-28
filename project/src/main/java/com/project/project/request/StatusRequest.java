package com.project.project.request;

import com.project.project.constant.ProjectStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StatusRequest(

        @NotNull
        UUID id,

        @NotNull
        ProjectStatus status

) {}
