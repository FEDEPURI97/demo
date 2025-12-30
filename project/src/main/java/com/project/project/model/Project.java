package com.project.project.model;

import com.project.project.constant.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    private Integer id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private BigDecimal budget;

    public void setStatus(ProjectStatus status){
        this.status = status.toString();
    }
}

