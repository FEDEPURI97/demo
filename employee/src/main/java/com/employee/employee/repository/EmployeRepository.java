package com.employee.employee.repository;

import com.employee.employee.constant.Role;
import com.employee.employee.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface EmployeRepository extends ReactiveCrudRepository<Employee, UUID> {

    Flux<Employee> findByRole(Role role);

    Flux<Employee> findByManagerId(UUID id);

    Flux<Employee> findByDepartmentId(UUID id);

}
