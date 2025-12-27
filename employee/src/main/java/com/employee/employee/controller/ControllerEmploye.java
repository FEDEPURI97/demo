package com.employee.employee.controller;

import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import com.employee.employee.service.ServiceEmployee;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("employees")
public class ControllerEmploye {

    private final ServiceEmployee serviceEmployee;

    @GetMapping
    public void getEmployees(){
        //Lista tutti i dipendenti
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") UUID id){
        return serviceEmployee.getEmployeeById(id);
    }
    @PostMapping
    public Mono<EmployeeDto> createEmployee(EmployeeRequest request){
        return serviceEmployee.createEmployee(request);
    }
    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(EmployeeRequest request){
        return ResponseEntity.ok(null);
    }
    @PutMapping("/{id}")
    public Mono<String> statusEmployee(@PathVariable("id") UUID id, Status status){
        return serviceEmployee.statusUser(id, status);

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
