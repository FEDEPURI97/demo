package com.project.project.service;

import com.project.project.constant.ProjectStatus;
import com.project.project.dto.ProjectDto;
import com.project.project.exception.DateException;
import com.project.project.exception.ProjectNotIdException;
import com.project.project.factory.ProjectMapper;
import com.project.project.model.Project;
import com.project.project.repository.ProjectRepository;
import com.project.project.request.ProjectsRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ServiceProjectImpl implements ServiceProject {

    private final ProjectRepository repositoryProject;
    private final ProjectMapper mapper;


    @Override
    public Flux<ProjectDto> getAllProjects() {
        return repositoryProject.findAll()
                .map(mapper::toDto);
    }

    @Override
    public Mono<ProjectDto> getProjectById(UUID id) {
        return repositoryProject.findById(id)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)));
    }

    @Override
    public Mono<ProjectDto> createProjects(ProjectsRequest request) {
        Project project = mapper.toModelFromRequest(request);
        project.setStartDate(LocalDate.now());
        project.setStatus(ProjectStatus.PLANNED);
        Mono<Project> model = repositoryProject.save(project);
        return model.map(mapper::toDto);
    }

    @Override
    public Mono<String> updateStatus(UUID id, ProjectStatus status) {
        return repositoryProject.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)))
                .flatMap(project -> {
                    project.setStatus(status);
                    if (status.equals(ProjectStatus.COMPLETED)) {
                        project.setEndDate(LocalDate.now());
                    }
                    repositoryProject.save(project);
                    return Mono.just("Status aggiornato con successo");
                });
    }

    @Override
    public Mono<String> deleteProjects(UUID id) {
        return repositoryProject.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)))
                .flatMap(employee ->
                        repositoryProject.delete(employee)
                                .then(Mono.just("Project eliminato con successo"))
                );
    }

    @Override
    public Mono<String> updateBudget(UUID id, BigDecimal budget) {
        return  repositoryProject.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)))
                .flatMap(project -> {
                    project.setBudget(budget);
                    repositoryProject.save(project);
                    return Mono.just("Salario aggiornato con successo");
                });
    }

    @Override
    public Mono<String> updateEndDate(UUID id, LocalDate date) {
        return repositoryProject.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)))
                .flatMap(project -> {
                    if (date.isAfter(project.getStartDate())) throw new DateException();
                    project.setEndDate(date);
                    repositoryProject.save(project);
                    return Mono.just("Fine data aggiornata con successo");
                });
    }
}
