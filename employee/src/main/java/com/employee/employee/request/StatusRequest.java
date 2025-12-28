package com.employee.employee.request;

import com.employee.employee.constant.Status;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StatusRequest(
        @NotNull UUID id,
        @NotNull Status status
) {}
