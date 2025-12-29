package com.employee.employee;

import com.employee.employee.constant.StatusEmployee;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.request.EmployeeRequest;
import com.employee.employee.request.SalaryRequest;
import com.employee.employee.request.StatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EmployeeIntegrationTest extends ConfigTest{

    @Test
    void createEmployee_validationError_integrationTest() {
        EmployeeRequest invalidRequest = new EmployeeRequest(
                "RSSMRA80A01H501U",
                "",
                "Rossi",
                "invalid-email",
                "1234567890",
                LocalDate.of(1980, 1, 1),
                new BigDecimal("3000")
        );

        webTestClient.post().uri("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorMessage -> {
                    assertTrue(errorMessage.contains("Nome non può essere vuoto"));
                    assertTrue(errorMessage.contains("Email non valida"));
                });
    }


    @Test
    void getEmployees_integrationTest() {
        log.info("Call get method for all employees");
        webTestClient.get().uri("/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .value(employees -> {
                    List<String> aspected = employees.stream()
                            .map(EmployeeDto::employeeCode)
                            .toList();
                    assertTrue(aspected.contains("RSSMRA80A01H501A"));
                    assertTrue(aspected.contains("VRDLGI90B12F205Y"));
                    aspected = employees.stream()
                            .map(EmployeeDto::email)
                            .toList();
                    assertTrue(aspected.contains("test.user@email.com"));
                    assertTrue(aspected.contains("jane.doe@email.com"));

                });
    }

    @Test
    void getEmployee_notId_integrationTest() {
        log.info("Call get method for employee by Id");
        webTestClient.get().uri("/employees/{id}", "10")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorMessage -> {
                    assertEquals("L'utente con il seguente id: 10 non è stato trovato",errorMessage);
                });
    }

    @Test
    void createEmployee_duplicate_integrationTest() {
        EmployeeRequest invalidRequest = new EmployeeRequest(
                "RSSMRA80A01H501U",
                "Mario",
                "Rossi",
                "test.user@email.com",
                "1234567890",
                LocalDate.of(1980, 1, 1),
                new BigDecimal("3000")
        );
        webTestClient.post().uri("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorMessage -> {
                    assertEquals("Email o codice fiscale già presente",errorMessage);
                });
    }

    @Test
    void createEmployee_integrationTest() {
        EmployeeRequest request = new EmployeeRequest(
                "RSSMRA80A01H501U",
                "Mario",
                "Rossi",
                "mario.rossi@email.it",
                "1234567890",
                LocalDate.of(1980,1,1),
                new BigDecimal("3000")
        );
        log.info("Call post method employees body: {}", request);
        webTestClient.post().uri("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .value(employee -> {
                    assertNotNull(employee.employeeCode());
                    assertEquals("RSSMRA80A01H501U",employee.employeeCode());
                    assertEquals("1234567890",employee.phoneNumber());
                    assertEquals("mario.rossi@email.it", employee.email());
                    assertEquals(StatusEmployee.SUSPENDED, employee.status());
                });
    }

    @Test
    void getEmployeeById_integrationTest() {
        log.info("Call get method for employee by Id");
        webTestClient.get().uri("/employees/{id}", "2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .value(employee -> {
                    assertEquals("VRDLGI90B12F205Y", employee.employeeCode());
                    assertEquals("0987654321",employee.phoneNumber());
                    assertEquals("jane.doe@email.com", employee.email());
                });
    }

    @Test
    void updateStatus_integrationTest() {
        log.info("Call PUT /status to update employee status");
        StatusRequest request = new StatusRequest(1, StatusEmployee.ON_LEAVE);
        webTestClient.put().uri("/employees/status")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertEquals("Status aggiornato con successo", response);
                });
    }

    @Test
    void updateSalary_integrationTest() {
        log.info("Call PUT /status to update employee status");
        SalaryRequest request = new SalaryRequest(1, new BigDecimal(5000));
        webTestClient.put().uri("/employees/salary")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertEquals("Salario aggiornato con successo", response);
                });
    }

    @Test
    void deleteEmployee_integrationTest() {
        Employee employee = new Employee(
                null,
                "name",
                "lastname",
                "test.delete@email.com",
                "",
                LocalDate.of(1980,1,1),
                "TEST123475PRCFTE",
                LocalDate.now(),
                null,
                StatusEmployee.ON_LEAVE.name(),
                new BigDecimal(1100)
        );

        Employee saved = repository.save(employee).block();
        assertNotNull(saved);
        webTestClient.delete()
                .uri("/employees/{id}", saved.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(msg -> assertEquals("Employee eliminato con successo", msg));
    }




}
