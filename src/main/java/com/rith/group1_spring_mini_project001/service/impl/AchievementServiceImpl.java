package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.model.model.Achievement;
import com.rith.group1_spring_mini_project001.repository.AchievementRepository;
import com.rith.group1_spring_mini_project001.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    @Override
    public List<Achievement> getAchievements(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return achievementRepository.getAchievements(offset, size);
    }

    @Override
    public List<Achievement> getAchievementsCurrentUser(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return achievementRepository.getAchievementsCurrentUser(offset, size);
    }
}
