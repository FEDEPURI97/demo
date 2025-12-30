package com.project.project.controller;

import com.project.project.exception.DateException;
import com.project.project.exception.DuplicateCustomException;
import com.project.project.exception.ProjectNotIdException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return Mono.just(ResponseEntity.badRequest().body(errors));
    }

    @ExceptionHandler(ProjectNotIdException.class)
    public Mono<ResponseEntity<String>> entityNotIdErrors(ProjectNotIdException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }

    @ExceptionHandler(DateException.class)
    public Mono<ResponseEntity<String>> dataErrors(DateException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateCustomException.class)
    public Mono<ResponseEntity<String>> duplicateCustomException(DuplicateCustomException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }


}
