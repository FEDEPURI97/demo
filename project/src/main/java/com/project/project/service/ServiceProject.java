package com.project.project.service;

import com.project.project.constant.ProjectStatus;
import com.project.project.dto.ProjectDto;
import com.project.project.request.ProjectsRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ServiceProject {

    Flux<ProjectDto> getAllProjects();

    Mono<ProjectDto> getProjectById(UUID id);

    Mono<ProjectDto> createProjects(ProjectsRequest request);

    Mono<String> updateStatus(UUID id, ProjectStatus status);

    Mono<String> deleteProjects(UUID id);

    Mono<String> updateSalary(UUID uuid, UUID uuid1);
}
