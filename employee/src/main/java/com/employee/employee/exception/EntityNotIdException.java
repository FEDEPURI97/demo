package com.employee.employee.exception;

import java.util.UUID;

public class EntityNotIdException extends RuntimeException {
    public EntityNotIdException(UUID uuid) {
        super(String.format("L'utente con il seguente id: %s non è stato trovato",uuid));
    }
}
