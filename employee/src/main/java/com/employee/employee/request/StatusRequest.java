package com.employee.employee.request;

import com.employee.employee.constant.Status;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull Integer id,
        @NotNull Status status
) {}
