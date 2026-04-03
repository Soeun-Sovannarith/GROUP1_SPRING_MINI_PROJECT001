package com.rith.group1_spring_mini_project001.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementLoginRequest {
    private String achievementTitle;
    private String achievementDescription;
    private String badge;
    private Integer xpRequired;
}
