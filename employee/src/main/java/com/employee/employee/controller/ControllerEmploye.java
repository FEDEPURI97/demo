package com.employee.employee.controller;

import com.employee.employee.service.ServiceEmployee;
import com.employee.employee.constant.Role;
import com.employee.employee.entity.Employee;
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
    public ResponseEntity<Employee> getEmploye(@PathVariable("id") UUID id){
        Employee employee = service.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    @PostMapping
    public ResponseEntity<Employee> createEmploye(Employee employee){
        Employee employeCreated = service.createEmployee(employee);
        return ResponseEntity.ok(employeCreated);
    }
    @PutMapping
    public ResponseEntity<Employee> updateEmploye(Employee  employee){
        Employee employeupdate = service.updateEmployee(employee);
        return ResponseEntity.ok(employeupdate);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmploye(@PathVariable("id") UUID id){
        return ResponseEntity.ok(service.disableUser(id));

    }
    @GetMapping("/{role}")
    public ResponseEntity<List<Employee>> getEmployeByRole(@PathVariable("role") Role role){
        return ResponseEntity.ok(service.getEmployeesByRole(role));
    }
    @GetMapping("/{idDepartment}")
    public ResponseEntity<List<Employee>> getEmployeByDepartment(@PathVariable("idDepartment") UUID idDepartment){
        return ResponseEntity.ok(service.getEmployeesByDepartmentId(idDepartment));
    }
    @GetMapping("/{idManager}")
    public ResponseEntity<List<Employee>> getEmployeByManager(@PathVariable("idManager") UUID idDepartment){
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
