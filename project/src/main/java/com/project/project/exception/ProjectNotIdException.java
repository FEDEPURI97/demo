package com.project.project.exception;

import java.util.UUID;

public class ProjectNotIdException extends RuntimeException {
    public ProjectNotIdException(UUID id) {
        super(String.format("Il progetto con il seguente id: %s non è stato trovato",id));
    }
}
