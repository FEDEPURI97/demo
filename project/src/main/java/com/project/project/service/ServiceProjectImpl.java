package com.project.project.service;

import com.project.project.constant.ProjectStatus;
import com.project.project.dto.ProjectDto;
import com.project.project.exception.DateException;
import com.project.project.exception.DuplicateCustomException;
import com.project.project.exception.ProjectNotIdException;
import com.project.project.factory.ProjectMapper;
import com.project.project.model.Project;
import com.project.project.repository.ProjectRepository;
import com.project.project.request.ProjectsRequest;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public Mono<ProjectDto> getProjectById(Integer id) {
        return repositoryProject.findById(id)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)));
    }

    @Override
    public Mono<ProjectDto> createProjects(ProjectsRequest request) {
        Project project = mapper.toModelFromRequest(request);
        project.setStatus(ProjectStatus.PLANNED);
        return repositoryProject.save(project).onErrorMap(e -> {
                    if (e instanceof DuplicateKeyException || e instanceof DataIntegrityViolationException) {
                        return new DuplicateCustomException("Nome progetto già presente");
                    }
                    return e;
                }).flatMap(projectDb -> Mono.just(mapper.toDto(projectDb)));
    }


    @Override
    public Mono<String> updateStatus(Integer id, ProjectStatus status) {
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
    public Mono<String> deleteProjects(Integer id) {
        return repositoryProject.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)))
                .flatMap(employee ->
                        repositoryProject.delete(employee)
                                .then(Mono.just("Project eliminato con successo"))
                );
    }

    @Override
    public Mono<String> updateBudget(Integer id, BigDecimal budget) {
        return  repositoryProject.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotIdException(id)))
                .flatMap(project -> {
                    project.setBudget(budget);
                    repositoryProject.save(project);
                    return Mono.just("Budget aggiornato con successo");
                });
    }

    @Override
    public Mono<String> updateEndDate(Integer id, LocalDate date) {
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
