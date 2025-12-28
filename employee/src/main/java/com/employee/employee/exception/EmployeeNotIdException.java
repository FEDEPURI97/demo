package com.employee.employee.exception;

import java.util.UUID;

public class EmployeeNotIdException extends RuntimeException {
    public EmployeeNotIdException(UUID uuid) {
        super(String.format("L'utente con il seguente id: %s non è stato trovato",uuid));
    }
}
