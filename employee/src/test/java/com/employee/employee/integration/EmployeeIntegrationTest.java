package com.employee.employee.integration;

import com.employee.employee.constant.StatusEmployee;
import com.employee.employee.dto.EmployeeDto;
import com.employee.employee.dto.UserRegisteredDto;
import com.employee.employee.request.EmployeeRequest;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class EmployeeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private KafkaSender<String, UserRegisteredDto> kafkaSender;

    @BeforeEach
    void setUp() {
        when(kafkaSender.send(
                ArgumentMatchers.<Publisher<SenderRecord<String, UserRegisteredDto, UUID>>>any()
        )).thenReturn(Flux.empty());
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
}
