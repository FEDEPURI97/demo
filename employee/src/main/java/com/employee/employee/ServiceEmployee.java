package com.employee.employee;

import com.employee.employee.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ServiceEmployee {
    Employee getEmployeeById(UUID id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);
}
