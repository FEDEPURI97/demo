package com.employee.employee.controller;

import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import com.employee.employee.service.ServiceEmployee;
import com.employee.employee.constant.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("employees")
public class ControllerEmploye {

    private final ServiceEmployee service;

    @GetMapping
    public void getEmployees(){
        //Lista tutti i dipendenti
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmploye(@PathVariable("id") UUID id){
        EmployeeDto employee = service.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmploye(EmployeeRequest request){
        EmployeeDto employeCreated = service.createEmployee(request);
        return ResponseEntity.ok(employeCreated);
    }
    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmploye(EmployeeRequest request){
        EmployeeDto employeupdate = service.updateEmployee(request);
        return ResponseEntity.ok(employeupdate);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmploye(@PathVariable("id") UUID id){
        return ResponseEntity.ok(service.disableUser(id));

    }
    @GetMapping("/{role}")
    public ResponseEntity<List<EmployeeDto>> getEmployeByRole(@PathVariable("role") Role role){
        return ResponseEntity.ok(service.getEmployeesByRole(role));
    }
    @GetMapping("/{idDepartment}")
    public ResponseEntity<List<EmployeeDto>> getEmployeByDepartment(@PathVariable("idDepartment") UUID idDepartment){
        return ResponseEntity.ok(service.getEmployeesByDepartmentId(idDepartment));
    }
    @GetMapping("/{idManager}")
    public ResponseEntity<List<EmployeeDto>> getEmployeByManager(@PathVariable("idManager") UUID idDepartment){
        return ResponseEntity.ok(service.getEmployeesByManagerId(idDepartment));
    }
    /*


    🧩 Microservizi principali (core)



3️⃣ Project Service

Gestisce:

progetti aziendali

date

budget

stato

4️⃣ Allocation Service

Gestisce:

allocazione dipendenti → progetti

percentuale (es. 50%)

periodo

     */

}
