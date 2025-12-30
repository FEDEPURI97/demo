package com.employee.employee.controller;

import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.request.EmployeeRequest;
import com.employee.employee.request.SalaryRequest;
import com.employee.employee.request.StatusRequest;
import com.employee.employee.service.ServiceEmployee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("employees")
@Validated
@Tag(name = "Employee Controller", description = "Gestione delle operazioni sugli impiegati")
public class ControllerEmploye {

    private final ServiceEmployee serviceEmployee;

    @GetMapping
    @Operation(
            summary = "Recupera tutti gli impiegati",
            description = "Restituisce la lista completa degli impiegati",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista degli impiegati recuperata correttamente")
            }
    )
    public Flux<EmployeeDto> getEmployees() {
        return serviceEmployee.getAllEmploye();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera un impiegato per ID",
            description = "Restituisce i dettagli di un impiegato dato il suo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Impiegato trovato"),
                    @ApiResponse(responseCode = "404", description = "Impiegato non trovato")
            }
    )
    public Mono<EmployeeDto> getEmployee(
            @Parameter(description = "ID dell'impiegato", required = true)
            @PathVariable("id") @NotNull Integer id) {
        return serviceEmployee.getEmployeeById(id);
    }

    @PostMapping
    @Operation(
            summary = "Crea un nuovo impiegato",
            description = "Crea un impiegato con i dati forniti nel body",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Impiegato creato con successo")
            }
    )
    public Mono<EmployeeDto> createEmployee(
            @Parameter(description = "Dati dell'impiegato", required = true)
            @Valid @RequestBody EmployeeRequest request) {
        return serviceEmployee.createEmployee(request);
    }

    @PutMapping("/status")
    @Operation(
            summary = "Aggiorna lo status di un impiegato",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status aggiornato correttamente")
            }
    )
    public Mono<String> statusEmployee(
            @Parameter(description = "Dati per aggiornare lo status", required = true)
            @RequestBody @Valid StatusRequest request) {
        return serviceEmployee.updateStatus(request.id(), request.statusEmployee());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Elimina un impiegato per ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Impiegato eliminato correttamente"),
                    @ApiResponse(responseCode = "404", description = "Impiegato non trovato")
            }
    )
    public Mono<String> deleteEmployee(
            @Parameter(description = "ID dell'impiegato", required = true)
            @PathVariable("id") @NotNull Integer id) {
        return serviceEmployee.deleteEmployee(id);
    }

    @PutMapping("/salary")
    @Operation(
            summary = "Aggiorna lo stipendio di un impiegato",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stipendio aggiornato correttamente")
            }
    )
    public Mono<String> salaryEmployee(
            @Parameter(description = "Dati per aggiornare lo stipendio", required = true)
            @RequestBody @Valid SalaryRequest request) {
        return serviceEmployee.updateSalary(request.idUser(), request.salary());
    }

}
