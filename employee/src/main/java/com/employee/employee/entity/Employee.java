package com.employee.employee.entity;

import com.employee.employee.constant.StatusEmployee;
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
public class Employee {

    @Id
    private Integer id;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String employeeCode;

    private LocalDate hireDate;

    private LocalDate endDate;

    private String status;

    private BigDecimal salary;

    public void setStatus(StatusEmployee statusEmployee){
        this.status = statusEmployee.toString();
    }

}
