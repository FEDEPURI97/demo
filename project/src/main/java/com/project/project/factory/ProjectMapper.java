package com.project.project.factory;

import com.project.project.dto.ProjectDto;
import com.project.project.model.Project;
import com.project.project.request.ProjectsRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toModelFromRequest(ProjectsRequest request);
}
