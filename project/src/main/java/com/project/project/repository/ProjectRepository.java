package com.project.project.repository;

import com.project.project.model.Project;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ReactiveCrudRepository<Project, Integer> {
}
