package com.employee.employee.request;

import com.employee.employee.constant.Status;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public record StatusRequest(
        @PathVariable("id") UUID id,
        @NotNull Status status
) {}
