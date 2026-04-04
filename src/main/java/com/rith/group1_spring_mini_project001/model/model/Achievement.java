package com.rith.group1_spring_mini_project001.model.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    private UUID achievementId;
    private String title;
    private String description;
    private String badge;
    private Integer xpRequired;
}
