package com.employee.employee.client;

import com.employee.employee.configuration.ServicesConfig;
import com.employee.employee.dto.DepartmentDto;
import com.employee.employee.dto.ProjectDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
public class Client {

    private final WebClient webClient;
    private final ServicesConfig servicesConfig;


    public Client(WebClient.Builder builder, ServicesConfig servicesConfig) {
        this.servicesConfig = servicesConfig;
        this.webClient = builder
                .baseUrl(this.servicesConfig.getBaseUrl())
                .build();
    }
    public Mono<DepartmentDto> getDepartmentById(UUID departmentId) {
        return webClient.get()
                .uri(servicesConfig.getDepartmentUri(), departmentId)
                .retrieve()
                .bodyToMono(DepartmentDto.class);
    }

    public Mono<List<ProjectDto>> getProjectsByIds(List<UUID> projectIds) {
        return webClient.post()
                .uri(servicesConfig.getProjectUri())
                .bodyValue(projectIds)
                .retrieve()
                .bodyToFlux(ProjectDto.class)
                .collectList();
    }

}
