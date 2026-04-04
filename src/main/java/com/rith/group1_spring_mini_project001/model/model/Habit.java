package com.rith.group1_spring_mini_project001.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    private UUID habitId;
    private String title;
    private String description;
    private FrenquencyType frequency;
    private Boolean isActive;
    private UUID appUserId;
    private Timestamp createdAt;
}
