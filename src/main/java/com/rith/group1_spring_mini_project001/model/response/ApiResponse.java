package com.rith.group1_spring_mini_project001.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private HttpStatus status;
    private String message;
    private T data;
    private Instant timestamp;
}