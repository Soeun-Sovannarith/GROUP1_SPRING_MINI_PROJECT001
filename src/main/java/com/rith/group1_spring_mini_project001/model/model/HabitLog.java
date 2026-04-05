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
public class HabitLog {
    private UUID habitLogId;
    private String status;
    private Integer xpEarned;
    private UUID habitId;
    private LocalDateTime createdAt;
}
