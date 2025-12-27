package com.employee.employee.entity;


import com.employee.employee.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRole {

    @Id
    private UUID id;

    private UUID employeeId;

    private Role role;

    private LocalDate startDate;

    private LocalDate endDate;


}
