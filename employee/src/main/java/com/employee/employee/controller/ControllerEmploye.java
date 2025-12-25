package com.employee.employee.controller;

import com.employee.employee.ServiceEmployee;
import com.employee.employee.constant.Role;
import com.employee.employee.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public void deleteEmploye(@PathVariable("id") UUID id){
        //Disattiva/elimina dipendente
    }
    @GetMapping("/{role}")
    public void getEmployeByRole(@PathVariable("role") Role role){
        //Filtra dipendenti per ruolo
    }
    @GetMapping("/{idDepartment}")
    public void getEmployeByDepartment(@PathVariable("idDepartment") UUID idDepartment){
        //Filtra dipendenti per reparto
    }
    @GetMapping("/{idManager}")
    public void getEmployeByManager(@PathVariable("idManager") UUID idDepartment){
        //Filtra dipendenti per reparto
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
