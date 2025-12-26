package com.employee.employee.util;

import com.employee.employee.entity.ProjectHistory;
import com.employee.employee.entity.SalaryHistory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Util {

    private Util(){

    }

    public List<UUID> filterForActualProject(List<ProjectHistory> projectHistory) {
        List<UUID> projectFilter = new ArrayList<>();
        for (ProjectHistory project : projectHistory) {
            if (project.getEndDate() == null){
                projectFilter.add(project.getProjectId());
            }
        }
        return projectFilter;
    }

    public BigDecimal getActualSalary(List<SalaryHistory> salaryHistory) {
        BigDecimal salaryActual = null;
        LocalDate endDate = null;
        for (SalaryHistory salary : salaryHistory) {
            if (salary.getEndDate() == null) return salary.getSalary();
            if (endDate == null) {
                endDate = salary.getEndDate();
                salaryActual = salary.getSalary();
            }
            salaryActual = endDate.isAfter(salary.getEndDate())  ? salaryActual : salary.getSalary();
        }
        return salaryActual;
    }
}
