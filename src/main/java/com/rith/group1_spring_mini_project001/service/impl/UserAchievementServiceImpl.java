package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.model.model.Achievement;
import com.rith.group1_spring_mini_project001.repository.UserAchievementRepository;
import com.rith.group1_spring_mini_project001.service.UserAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAchievementServiceImpl implements UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;


    @Override
    public List<Achievement> getUserAchievementsCurrentUser(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return userAchievementRepository.getgetUserAchievementsCurrentUser(offset,size) ;
    }
}
