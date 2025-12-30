package com.project.project.request;

import com.project.project.constant.ProjectStatus;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(

        @NotNull
        Integer id,

        @NotNull
        ProjectStatus status

) {}
