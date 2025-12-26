package com.employee.employee.repository;

import com.employee.employee.constant.Role;
import com.employee.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findByRole(Role role);

    List<Employee> findByManagerId(UUID id);

    List<Employee> findByDepartmentId(UUID id);

}
