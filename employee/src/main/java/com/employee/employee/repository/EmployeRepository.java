package com.employee.employee.repository;

import com.employee.employee.entity.Employee;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface EmployeRepository extends ReactiveCrudRepository<Employee, UUID> {

    @Query("UPDATE employee SET status = :status WHERE id = :id")
    Mono<Integer> updateStatus(UUID id, String status);
    @Query("UPDATE employee SET salary = :salary WHERE id = :id")
    Mono<Integer> updateSalary(UUID userId, BigDecimal salary);

}
