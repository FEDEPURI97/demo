package com.employee.employee;

import com.employee.employee.dto.UserRegisteredDto;
import com.employee.employee.repository.EmployeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.UUID;

import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public abstract class ConfigTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected EmployeRepository repository;

    @MockitoBean
    private KafkaSender<String, UserRegisteredDto> kafkaSender;

    @BeforeEach
    void setUp() {
        when(kafkaSender.send(
                ArgumentMatchers.<Publisher<SenderRecord<String, UserRegisteredDto, UUID>>>any()
        )).thenReturn(Flux.empty());
    }

}
