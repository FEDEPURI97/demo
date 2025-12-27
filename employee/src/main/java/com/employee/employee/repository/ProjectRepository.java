package com.employee.employee.repository;

import com.employee.employee.entity.ProjectHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProjectRepository extends ReactiveCrudRepository<ProjectHistory, UUID> {

    Flux<ProjectHistory> findByEmployeeId(UUID id);

}
