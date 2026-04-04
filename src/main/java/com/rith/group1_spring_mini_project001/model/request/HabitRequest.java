package com.rith.group1_spring_mini_project001.model.request;

import com.rith.group1_spring_mini_project001.model.model.FrenquencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitRequest {
    private String title;
    private String description;
    private FrenquencyType frequency;
}
