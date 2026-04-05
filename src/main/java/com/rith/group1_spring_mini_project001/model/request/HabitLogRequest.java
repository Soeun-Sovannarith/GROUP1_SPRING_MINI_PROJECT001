package com.rith.group1_spring_mini_project001.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitLogRequest {
    private String status;
    private UUID habitId;
}
