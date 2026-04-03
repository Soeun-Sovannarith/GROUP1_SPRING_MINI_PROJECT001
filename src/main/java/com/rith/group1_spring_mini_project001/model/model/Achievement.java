package com.rith.group1_spring_mini_project001.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {
    private UUID achievementId;
    private String achievementTitle;
    private String achievementDescription;
    private String badge;
    private Integer xpRequired;

}
