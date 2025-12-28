package com.employee.employee.dto;

import java.util.UUID;

public record UserRegisteredDto(
    UUID id,
    String email,
    String activationLink
){}
