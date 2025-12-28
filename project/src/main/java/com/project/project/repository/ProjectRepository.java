package com.project.project.repository;

import com.project.project.model.Project;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface ProjectRepository extends ReactiveCrudRepository<Project, UUID> {
    @Query("UPDATE project SET status = :status WHERE id = :id")
    Mono<Integer> updateStatus(UUID id, String status);
    @Query("UPDATE project SET budget = :budget WHERE id = :id")
    Mono<Integer> updateBudget(UUID id, BigDecimal budget);
}
