package com.employee.employee.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServicesConfig {

    private final String baseUrl;
    private final String departmentUri;
    private final String projectsUri;
    private final String projectUri;

    public ServicesConfig(@Value("${services.base.url}") String baseUrl,
                          @Value("${services.department.uri}") String departmentUri,
                          @Value("${services.projects.uri}") String projectsUri,
                          @Value("${services.project.uri}") String projectUri) {
        this.baseUrl = baseUrl;
        this.departmentUri = departmentUri;
        this.projectsUri = projectsUri;
        this.projectUri = projectUri;
    }
}
