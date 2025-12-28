package com.project.project.dto;

import com.project.project.constant.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectDto(

        String name,

        String description,

        LocalDate startDate,

        LocalDate endDate,

        ProjectStatus status,

        BigDecimal budget

) {
}
