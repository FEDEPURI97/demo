package com.employee.employee.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("employees")
public class ControllerEmploye {


    @GetMapping
    public void getEmployees(){
        //Lista tutti i dipendenti
    }
    @GetMapping("/{id}")
    public void getEmploye(Integer id){
        //Recupera dettaglio dipendente
    }
    @PostMapping
    public void createEmploye(Integer id){
        //Crea un nuovo dipendente
    }
    @PutMapping("/{id}")
    public void updateEmploye(Integer id){
        //Aggiorna dati dipendente
    }
    @DeleteMapping("/{id}")
    public void deleteEmploye(Integer id){
        //Disattiva/elimina dipendente
    }
    @GetMapping("/{role}")
    public void getEmployeByRole(String role){
        //Filtra dipendenti per ruolo
    }
    @GetMapping("/{department}")
    public void getEmployeByDepartment(String department){
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
