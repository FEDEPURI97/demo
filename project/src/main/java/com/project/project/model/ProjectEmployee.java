package com.project.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEmployee {

    private UUID projectId;

    private UUID employeeId;

}
