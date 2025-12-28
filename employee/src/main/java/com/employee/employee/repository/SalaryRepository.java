package com.employee.employee.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface SalaryRepository extends ReactiveCrudRepository<SalaryHistory, UUID> {

    Flux<SalaryHistory> findByEmployeeId(UUID id);

}
