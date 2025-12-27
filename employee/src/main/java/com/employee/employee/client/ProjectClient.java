package com.employee.employee.client;

import com.employee.employee.dto.ProjectDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public class ProjectClient {

    private final WebClient webClient;

    public ProjectClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://project-service")
                .build();
    }

    public Flux<ProjectDto> getProjectById(UUID project) {
        return webClient.get()
                .uri("/project/{id}",project)
                .retrieve()
                .bodyToFlux(ProjectDto.class);
    }
}
