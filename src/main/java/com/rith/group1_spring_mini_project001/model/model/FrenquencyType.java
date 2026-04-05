package com.rith.group1_spring_mini_project001.model.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FrenquencyType {
    DAILY,
    WEEKLY,
    MONTHLY;

    public String toLower(){
        return name().toLowerCase();
    }

    @JsonValue
    public String toValue(){
        return name().toLowerCase();
    }
}
