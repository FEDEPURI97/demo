package com.employee.employee.entity;

import com.employee.employee.constant.Level;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employe {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String employeeCode;

    private UUID departmentId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Level level;

    private UUID managerId;

    private LocalDate hireDate;

    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
