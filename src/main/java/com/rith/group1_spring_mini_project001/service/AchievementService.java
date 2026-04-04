package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.model.Achievement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AchievementService {
    List<Achievement> getAchievements(Integer page, Integer size);

    List<Achievement> getAchievementsCurrentUser(UUID userId, Integer page, Integer size);
}
