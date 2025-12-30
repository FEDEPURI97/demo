package com.project.project;

import com.project.project.repository.ProjectRepository;
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
    protected ProjectRepository repository;


    @Container
    static CustomPostgresContainer postgresContainer = new CustomPostgresContainer()
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () ->
                "r2dbc:postgresql://" + postgresContainer.getHost() + ":" +
                        postgresContainer.getFirstMappedPort() + "/" + postgresContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
        registry.add("spring.flyway.enabled", () -> false);
    }
    

}
