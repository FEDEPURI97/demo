package com.employee.employee.repository;

import com.employee.employee.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeRepository extends ReactiveCrudRepository<Employee, Integer> {

}
