package com.project.project.exception;

public class ProjectNotIdException extends RuntimeException {
    public ProjectNotIdException(Integer id) {
        super(String.format("Il progetto con il seguente id: %s non è stato trovato",id));
    }
}
