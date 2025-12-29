package com.employee.employee;

import com.employee.employee.constant.StatusEmployee;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.UserRegisteredDto;
import com.employee.employee.entity.Employee;
import com.employee.employee.repository.EmployeRepository;
import com.employee.employee.request.EmployeeRequest;
import com.employee.employee.request.SalaryRequest;
import com.employee.employee.request.StatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class EmployeeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeRepository repository;

    @MockitoBean
    private KafkaSender<String, UserRegisteredDto> kafkaSender;

    @BeforeEach
    void setUp() {
        when(kafkaSender.send(
                ArgumentMatchers.<Publisher<SenderRecord<String, UserRegisteredDto, UUID>>>any()
        )).thenReturn(Flux.empty());
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
        Employee employee = new Employee();
        employee.setEmployeeCode("TEST123475PRCFTE");
        employee.setEmail("test.delete@email.com");
        employee.setStatus(StatusEmployee.ACTIVE);
        employee.setDateOfBirth(LocalDate.of(1980,1,1));
        employee.setSalary(new BigDecimal(1100));
        employee.setName("name");
        employee.setLastName("last name");

        repository.save(employee)
                .map(saved -> {
                    return webTestClient.delete()
                            .uri("/employees/{id}", saved.getId())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(String.class)
                            .value(msg -> assertEquals("Employee eliminato con successo", msg));
                });
    }




}
