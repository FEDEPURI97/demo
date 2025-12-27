package com.employee.employee.repository;

import com.employee.employee.entity.RoleHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface RoleHistoryRepository extends ReactiveCrudRepository<RoleHistory, UUID> {
    Flux<RoleHistory> findByEmployeeId(UUID userId);
}
