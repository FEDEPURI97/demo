package com.employee.employee.factory;

import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.request.EmployeeRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toModelFromRequest(EmployeeRequest request);

    EmployeeDto toDto(Employee employee);

}
