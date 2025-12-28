package com.employee.employee.controller;

import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import com.employee.employee.request.SalaryRequest;
import com.employee.employee.request.StatusRequest;
import com.employee.employee.service.ServiceEmployee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("employees")
@Validated
public class ControllerEmploye {

    private final ServiceEmployee serviceEmployee;

    @GetMapping
    public Flux<EmployeeDto> getEmployees(){
        return serviceEmployee.getAllEmploye();
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") @NotNull UUID id){
        return serviceEmployee.getEmployeeById(id);
    }

    @PostMapping
    public Mono<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeRequest request){
        return serviceEmployee.createEmployee(request);
    }

    @PutMapping("/status")
    public Mono<String> statusEmployee(@RequestBody @Valid StatusRequest request){
        return serviceEmployee.updateStatus(request.id(), request.status());
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteEmployee(@PathVariable("id")@NotNull UUID id){
        return serviceEmployee.deleteEmployee(id);
    }

    @PutMapping("salary")
    public Mono<String> salaryEmployee(@RequestBody @Valid SalaryRequest request){
        return serviceEmployee.updateSalary(request.idUser(), request.salary());
    }

}
