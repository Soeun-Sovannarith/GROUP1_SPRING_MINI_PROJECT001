package com.rith.group1_spring_mini_project001.exception;

import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(
            BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.<Void>builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Invalid email or password")
                        .build());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<Void>> handleDisabled(
            DisabledException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.<Void>builder()
                        .status(HttpStatus.FORBIDDEN)
                        .message("Account not verified. Please check your email.")
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntime(
            RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Void>builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(InvalidFileException.class)
    public ProblemDetail handleInvalidFile(InvalidFileException ex) {
        // ProblemDetail is a built-in Spring 6 / Boot 3+ class for RFC 7807
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setTitle("Bad Request");
        problemDetail.setInstance(URI.create("/api/v1/files/upload-file"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }


}