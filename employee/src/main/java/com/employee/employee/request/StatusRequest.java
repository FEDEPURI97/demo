package com.employee.employee.request;

import com.employee.employee.constant.StatusEmployee;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull Integer id,
        @NotNull StatusEmployee statusEmployee
) {}
