package com.employee.employee.exception;

public class DuplicateCustomException extends RuntimeException {
    public DuplicateCustomException(String message) {
        super(message);
    }
}
