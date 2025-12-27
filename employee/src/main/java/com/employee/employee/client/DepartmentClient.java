package com.employee.employee.client;

import com.employee.employee.dto.DepartmentDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class DepartmentClient {

    private final WebClient webClient;


    public DepartmentClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://department-service")
                .build();
    }
    public Mono<DepartmentDto> getDepartmentById(UUID departmentId) {
        return webClient.get()
                .uri("/departments/{id}", departmentId)
                .retrieve()
                .bodyToMono(DepartmentDto.class);
    }
}
