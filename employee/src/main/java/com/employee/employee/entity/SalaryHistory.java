package com.employee.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryHistory {

    @Id
    private UUID id;

    private UUID employeeId;

    private BigDecimal salary;

    private LocalDate startDate;

    private LocalDate endDate;

}
