package com.employee.employee.service;

import com.employee.employee.client.DepartmentClient;
import com.employee.employee.client.ProjectClient;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.DepartmentDto;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.ProjectDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.exception.EntityNotIdException;
import com.employee.employee.factory.EmployeeMapper;
import com.employee.employee.repository.EmployeRepository;
import com.employee.employee.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceEmployeeImpl implements ServiceEmployee{

    private final EmployeRepository repository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentClient departmentClient;
    private final ProjectClient projectClient;
    private final Util util;

    public ServiceEmployeeImpl(EmployeRepository repository, EmployeeMapper employeeMapper, DepartmentClient departmentClient, ProjectClient projectClient, Util util) {
        this.repository = repository;
        this.employeeMapper = employeeMapper;
        this.departmentClient = departmentClient;
        this.projectClient = projectClient;
        this.util = util;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(UUID id) {
        Employee model = repository.findById(id).orElseThrow(()->
                new EntityNotIdException(""));
        DepartmentDto departmentDto = departmentClient.getDepartmentById(model.getDepartmentId());
        List<UUID> projectList = util.filterForActualProject(model.getProjectHistory());
        List<ProjectDto> projectDto = projectClient.getProjectById(projectList);
        BigDecimal salary = util.getActualSalary(model.getSalaryHistory());
        return employeeMapper.toDto(model, salary, projectDto,departmentDto);
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
