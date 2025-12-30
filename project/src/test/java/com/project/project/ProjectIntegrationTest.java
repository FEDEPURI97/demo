package com.project.project;

import com.project.project.constant.ProjectStatus;
import com.project.project.dto.ProjectDto;
import com.project.project.model.Project;
import com.project.project.request.BudgetRequest;
import com.project.project.request.ProjectsRequest;
import com.project.project.request.StatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ProjectIntegrationTest extends ConfigTest {

    @Test
    void createEmployee_validationError_integrationTest() {
        ProjectsRequest invalidRequest = new ProjectsRequest(
                "TestProject",
                "Test create Employee invalid reuqest",
                null,
                LocalDate.now(),
                null
        );

        webTestClient.post().uri("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorMessage -> {
                    assertEquals("Salary non può essere nulla",errorMessage);
                });
    }


    @Test
    void getEmployees_integrationTest() {
        log.info("Call get method for all project");
        webTestClient.get().uri("/projects")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProjectDto.class)
                .value(projects -> {
                    List<String> aspected = projects.stream()
                            .map(ProjectDto::name)
                            .toList();
                    assertTrue(aspected.contains("Progetto Alpha"));
                    assertTrue(aspected.contains("Progetto Beta"));
                    aspected = projects.stream()
                            .map(ProjectDto::description)
                            .toList();
                    assertTrue(aspected.contains("Descrizione del progetto Alpha"));
                    assertTrue(aspected.contains("Descrizione del progetto Beta"));

                });
    }

    @Test
    void getEmployee_notId_integrationTest() {
        log.info("Call get method for employee by Id");
        webTestClient.get().uri("/projects/{id}", "10")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorMessage -> {
                    assertEquals("Il progetto con il seguente id: 10 non è stato trovato", errorMessage);
                });
    }

    @Test
    void createEmployee_duplicate_integrationTest() {
        ProjectsRequest invalidRequest = new ProjectsRequest(
                "Progetto Alpha","Test description",new BigDecimal(100000),LocalDate.now(),LocalDate.now()
        );
        webTestClient.post().uri("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorMessage -> {
                    assertEquals("Nome progetto già presente",errorMessage);
                });
    }

    @Test
    void createEmployee_integrationTest() {
        ProjectsRequest request = new ProjectsRequest(
                "TestProject",
                "Test create Employee invalid reuqest",
                new BigDecimal(5000000),
                LocalDate.now(),
                null
        );
        log.info("Call post method employees body: {}", request);
        webTestClient.post().uri("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProjectDto.class)
                .value(project -> {
                    assertEquals(request.name(),project.name());
                    assertEquals(request.description(),project.description());
                    assertEquals(request.budget(), project.budget());
                    assertEquals(ProjectStatus.PLANNED, project.status());
                });
    }

    @Test
    void getEmployeeById_integrationTest() {
        log.info("Call get method for project by Id");
        webTestClient.get().uri("/projects/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProjectDto.class)
                .value(projectDto -> {
                    assertEquals("Progetto Alpha", projectDto.name());
                    assertEquals("Descrizione del progetto Alpha",projectDto.description());
                    assertEquals(LocalDate.parse("2025-01-01"), projectDto.startDate());
                    assertEquals(ProjectStatus.IN_PROGRESS, projectDto.status());
                });
    }

    @Test
    void updateStatus_integrationTest() {
        log.info("Call PUT /status to update projects status");
        StatusRequest request = new StatusRequest(2, ProjectStatus.IN_PROGRESS);
        webTestClient.put().uri("/projects/status")
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
        log.info("Call PUT /status to update projects status");
        BudgetRequest request = new BudgetRequest(1, new BigDecimal(150000));
        webTestClient.put().uri("/projects/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertEquals("Budget aggiornato con successo", response);
                });
    }

    @Test
    void deleteEmployee_integrationTest() {
        Project project = new Project(
                null,"test","test",LocalDate.now(),null,ProjectStatus.PLANNED.name(),new BigDecimal(1000000)
        );
        Project saved = repository.save(project).block();
        assertNotNull(saved);
        webTestClient.delete()
                .uri("/projects/{id}", saved.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(msg -> assertEquals("Project eliminato con successo", msg));
    }




}
