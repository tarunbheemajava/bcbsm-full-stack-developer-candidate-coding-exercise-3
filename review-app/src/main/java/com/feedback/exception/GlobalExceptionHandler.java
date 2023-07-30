package com.feedback.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ResponseStatusException.class, BadCredentialsException.class })
    ProblemDetail handle(ResponseStatusException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getStatusCode(),ex.getReason());
        problemDetail.setTitle("TITLE");
        problemDetail.setType(URI.create("https://www.reviewapp.com/errors/specific-error"));
        return problemDetail;
    }


    @ExceptionHandler(value = { Exception.class })
    ProblemDetail handle(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),ex.getMessage());
        problemDetail.setTitle("TITLE");
        problemDetail.setType(URI.create("https://www.reviewapp.com/errors/specific-error"));
        return problemDetail;
    }



}