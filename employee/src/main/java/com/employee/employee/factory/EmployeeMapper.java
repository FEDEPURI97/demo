package com.employee.employee.factory;

import com.employee.employee.dto.DepartmentDto;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.ManagerDto;
import com.employee.employee.dto.ProjectDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.request.EmployeeRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee model, List<ProjectDto> projectsDto, DepartmentDto departmentDto, ManagerDto managerDto);

    Employee toModelFromRequest(EmployeeRequest request);

    EmployeeDto toDtoCreate(Employee employee);

    ManagerDto toManagerDto(Employee employee);
}
