package com.employee.employee.entity;

import com.employee.employee.constant.Level;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
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
public class Employee {

    @Id
    private UUID id;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String employeeCode;

    private UUID departmentId;

    private String role;

    private String level;

    private UUID managerId;

    private LocalDate hireDate;

    private LocalDate endDate;

    private Status status;

    private BigDecimal salary;

    public void setRole(Role role) {
        this.role = role.toString();
    }

    public void setLevel(Level level) {
        this.level = level.toString();
    }
}
