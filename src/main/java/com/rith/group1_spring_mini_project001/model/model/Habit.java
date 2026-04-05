package com.rith.group1_spring_mini_project001.model.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    private UUID habitId;
    private String title;
    private String description;
    private String frequency;
    private Boolean isActive;
    private UUID appUserId;
    private LocalDateTime createdAt;
}
