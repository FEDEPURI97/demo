package com.project.project.controller;

import com.project.project.request.StatusRequest;
import com.project.project.dto.ProjectDto;
import com.project.project.request.EmployeeProjectRequest;
import com.project.project.request.ProjectsRequest;
import com.project.project.service.ServiceProject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("project")
@Validated
public class ControllerProject {

    private final ServiceProject serviceProject;

    @GetMapping
    public Flux<ProjectDto> getProjects(){
        return serviceProject.getAllProjects();
    }

    @GetMapping("/{id}")
    public Mono<ProjectDto> getProjects(@PathVariable("id") @NotNull UUID id){
        return serviceProject.getProjectById(id);
    }

    @PostMapping
    public Mono<ProjectDto> createProject(@Valid @RequestBody ProjectsRequest request){
        return serviceProject.createProjects(request);
    }

    @PutMapping("/status")
    public Mono<String> statusProject(@RequestBody @Valid StatusRequest request){
        return serviceProject.updateStatus(request.id(), request.status());
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteProject(@PathVariable("id")@NotNull UUID id){
        return serviceProject.deleteProjects(id);
    }

    @PutMapping("addEmployee")
    public Mono<String> addEmployeeToProject(@RequestBody @Valid EmployeeProjectRequest request){
        return serviceProject.updateSalary(request.idUser(), request.idProject());
    }

}
