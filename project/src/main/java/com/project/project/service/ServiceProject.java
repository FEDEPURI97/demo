package com.project.project.service;

import com.project.project.constant.ProjectStatus;
import com.project.project.dto.ProjectDto;
import com.project.project.request.ProjectsRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ServiceProject {

    Flux<ProjectDto> getAllProjects();

    Mono<ProjectDto> getProjectById(Integer id);

    Mono<ProjectDto> createProjects(ProjectsRequest request);

    Mono<String> updateStatus(Integer id, ProjectStatus status);

    Mono<String> deleteProjects(Integer id);

    Mono<String> updateBudget(Integer id, BigDecimal budget);

    Mono<String> updateEndDate(Integer id,LocalDate date);
}
