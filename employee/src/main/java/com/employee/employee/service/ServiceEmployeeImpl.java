package com.employee.employee.service;

import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.entity.Employee;
import com.employee.employee.repository.EmployeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceEmployeeImpl implements ServiceEmployee{

    private final EmployeRepository repository;

    public ServiceEmployeeImpl(EmployeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        employee = repository.save(employee);
        return employee;
    }

    @Override
    @Transactional
    public String statusUser(Employee employee , Status status) {
        employee.setStatus(status);
        repository.save(employee);
        return String.format("Modifica status in: %s, dello user: %s %s avvenuto con successo",
                status, employee.getName(), employee.getLastName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByRole(Role role) {
        return repository.findByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartmentId(UUID idDepartment) {
        return repository.findByDepartmentId(idDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByManagerId(UUID idManager) {
        return repository.findByManagerId(idManager);
    }
}
