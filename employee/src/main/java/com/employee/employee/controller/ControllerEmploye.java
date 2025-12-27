package com.employee.employee.controller;

import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import com.employee.employee.service.ServiceEmployee;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("employees")
public class ControllerEmploye {

    private final ServiceEmployee serviceEmployee;

    @GetMapping
    public Flux<EmployeeDto> getEmployees(){
        return serviceEmployee.getAllEmploye();
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") UUID id){
        return serviceEmployee.getEmployeeById(id);
    }

    @PostMapping
    public Mono<EmployeeDto> createEmployee(EmployeeRequest request){
        return serviceEmployee.createEmployee(request);
    }

    @PutMapping("status/{id}")
    public Mono<String> statusEmployee(@PathVariable("id") UUID id, Status status){
        return serviceEmployee.statusUser(id, status);

    }

    @PutMapping("project/{idUser}/{idProject}")
    public Mono<String> projectEmployee(@PathVariable("idUser") UUID idUser,@PathVariable("idProject") UUID idProject){
        return serviceEmployee.addProjectToEmployee(idUser, idProject);
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteEmployee(@PathVariable("id")UUID id){
        return serviceEmployee.deleteEmployee(id);
    }

    @PutMapping("role/{idUser}")
    public Mono<String> projectEmployee(@PathVariable("idUser") UUID idUser,Role role){
        return serviceEmployee.updateRole(idUser, role);
    }

    @PutMapping("salary/{idUser}")
    public Mono<String> salaryEmployee(@PathVariable("idUser") UUID idUser, BigDecimal salary){
        return serviceEmployee.salaryUser(idUser, salary);
    }

    @GetMapping("/{role}")
    public Flux<EmployeeDto> getEmployeeByRole(@PathVariable("role") Role role){
        return serviceEmployee.getEmployeesByRole(role);
    }
    @GetMapping("/{idDepartment}")
    public Flux<EmployeeDto> getEmployeeByDepartment(@PathVariable("idDepartment") UUID idDepartment){
        return serviceEmployee.getEmployeesByDepartmentId(idDepartment);
    }
    @GetMapping("/{idManager}")
    public Flux<EmployeeDto> getEmployeeByManager(@PathVariable("idManager") UUID idManager){
        return serviceEmployee.getEmployeesByManagerId(idManager);
    }

}
