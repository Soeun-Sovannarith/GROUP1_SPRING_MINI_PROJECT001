package com.rith.group1_spring_mini_project001.model.response;

import com.rith.group1_spring_mini_project001.model.model.UserApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitResponse {
    private UUID habitId;
    private String title;
    private String description;
    private String frequency;
    private Boolean isActive;
    private UserApp appUserResponse;
    private Timestamp createAt;
}
