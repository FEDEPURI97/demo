package com.employee.employee;

import com.employee.employee.repository.EmployeRepository;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Testcontainers
public abstract class ConfigTest {

    public static class CustomPostgresContainer extends PostgreSQLContainer<CustomPostgresContainer> {
        public CustomPostgresContainer() {
            super("postgres:17");
        }
    }

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected EmployeRepository repository;

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("apache/kafka:3.7.2")
    );

    @Container
    static CustomPostgresContainer postgresContainer = new CustomPostgresContainer()
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");


    @BeforeAll
    static void createTopic() {
        Map<String, Object> config = Map.of(
                "bootstrap.servers", kafkaContainer.getBootstrapServers()
        );
        try (AdminClient adminClient = AdminClient.create(config)) {
            NewTopic topic = new NewTopic("user-registered", 1, (short) 1);
            adminClient.createTopics(List.of(topic)).all().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () ->
                "r2dbc:postgresql://" + postgresContainer.getHost() + ":" +
                        postgresContainer.getFirstMappedPort() + "/" + postgresContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
        registry.add("spring.flyway.enabled", () -> false);
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }
    

}
