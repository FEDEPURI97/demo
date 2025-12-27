package com.employee.employee.factory;

import com.employee.employee.dto.DepartmentDto;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.ProjectDto;
import com.employee.employee.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee model, List<ProjectDto> projectsDto, DepartmentDto departmentDto);
}
