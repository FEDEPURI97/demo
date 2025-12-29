package com.employee.employee.exception;

public class EmployeeNotIdException extends RuntimeException {
    public EmployeeNotIdException(Integer id) {
        super(String.format("L'utente con il seguente id: %s non è stato trovato",id));
    }
}
