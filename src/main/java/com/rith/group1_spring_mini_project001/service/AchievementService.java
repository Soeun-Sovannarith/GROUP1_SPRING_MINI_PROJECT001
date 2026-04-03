package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.model.Achievement;

import java.util.List;

public interface AchievementService {
    List<Achievement> getAllAchievements(Integer page, Integer size);
}
