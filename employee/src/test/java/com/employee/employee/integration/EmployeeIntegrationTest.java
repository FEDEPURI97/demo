package com.employee.employee.integration;

import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.UserRegisteredDto;
import com.employee.employee.request.EmployeeRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class EmployeeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

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

        webTestClient.post().uri("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .value(employee -> {
                    assertNotNull(employee.employeeCode());
                    assertEquals("mario.rossi@email.it", employee.email());
                });
    }
}
