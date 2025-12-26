package com.employee.employee.controller;

import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("employees")
public class ControllerEmploye {

    @GetMapping
    public void getEmployees(){
        //Lista tutti i dipendenti
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") UUID id){
        return ResponseEntity.ok(null);
    }
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeRequest request){
        return ResponseEntity.ok(null);
    }
    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(EmployeeRequest request){
        return ResponseEntity.ok(null);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> statusEmployee(@PathVariable("id") UUID id, Status status){
        return ResponseEntity.ok(null);

    }
    @GetMapping("/{role}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByRole(@PathVariable("role") Role role){
        return ResponseEntity.ok(null);
    }
    @GetMapping("/{idDepartment}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByDepartment(@PathVariable("idDepartment") UUID idDepartment){
        return ResponseEntity.ok(null);
    }
    @GetMapping("/{idManager}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByManager(@PathVariable("idManager") UUID idDepartment){
        return ResponseEntity.ok(null);
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
