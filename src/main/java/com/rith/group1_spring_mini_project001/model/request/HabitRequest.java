package com.rith.group1_spring_mini_project001.model.request;

import com.rith.group1_spring_mini_project001.model.model.FrenquencyType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitRequest {
    @NonNull
    @NotBlank
    private String title;
    private String description;
    private FrenquencyType frequency;
}
