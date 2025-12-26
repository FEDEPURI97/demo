package com.employee.employee.client;

import com.employee.employee.dto.ProjectDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
public class ProjectClient {

    private final WebClient webClient;

    public ProjectClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://project-service")
                .build();
    }

    public List<ProjectDto> getProjectById(List<UUID> projectList) {
        return webClient.post()
                .uri("/project/batch")
                .bodyValue(projectList)
                .retrieve()
                .bodyToFlux(ProjectDto.class)
                .collectList()
                .block();
    }
}
