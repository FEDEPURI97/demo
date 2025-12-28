package com.project.project.exception;

public class DateException extends RuntimeException {
    public DateException() {
        super("La data fine progetto non puo essere prima dell'inizio");
    }
}
