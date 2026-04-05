package com.rith.group1_spring_mini_project001.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed. Please check your input."
        );
        pd.setType(URI.create("about:blank"));
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {

            errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        pd.setProperty("errors", errors);
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid username, email, or password. Please check your credentials and try again."
        );
        pd.setType(URI.create("about:blank"));
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(DisabledException.class)
    public ProblemDetail handleDisabled(
            DisabledException ex,
            HttpServletRequest request
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Account not verified. Please verify your account before logging in."
        );
        pd.setType(URI.create("about:blank"));
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(InvalidFileException.class)
    public ProblemDetail handleInvalidFile(
            InvalidFileException ex,
            HttpServletRequest request
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        pd.setType(URI.create("about:blank"));
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        ex.printStackTrace(); // Optional: Prints the stack trace
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error: " + ex.getMessage() + " - Cause: " + (ex.getCause() != null ? ex.getCause().getMessage() : "none")
        );
        pd.setType(URI.create("about:blank"));
        pd.setTitle("Internal Server Error");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }


    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthentication(
            AuthenticationException ex,
            HttpServletRequest request
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Authentication failed. Please check your credentials and try again."
        );
        pd.setType(URI.create("about:blank"));
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

}
