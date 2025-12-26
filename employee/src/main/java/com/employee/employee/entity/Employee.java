package com.employee.employee.entity;

import com.employee.employee.constant.Level;
import com.employee.employee.constant.Role;
import com.employee.employee.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

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

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoryRole> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Level level;

    private UUID managerId;

    private LocalDate hireDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaryHistory> salaryHistory = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<ProjectHistory> projectHistory = new ArrayList<>();

}
