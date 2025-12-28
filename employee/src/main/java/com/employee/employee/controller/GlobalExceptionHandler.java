package com.employee.employee.controller;

import com.employee.employee.exception.DuplicateCustomException;
import com.employee.employee.exception.EmployeeNotIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<String>> handleValidationErrors(WebExchangeBindException ex) {
        String errors = ex.getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Mono.just(ResponseEntity.badRequest().body(errors));
    }

    @ExceptionHandler(DuplicateCustomException.class)
    public Mono<ResponseEntity<String>> duplicateErrors(DuplicateCustomException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }

    @ExceptionHandler(EmployeeNotIdException.class)
    public Mono<ResponseEntity<String>> entityNotIdErrors(EmployeeNotIdException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }


}
