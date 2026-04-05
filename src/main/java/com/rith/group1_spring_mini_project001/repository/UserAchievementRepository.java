package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.Achievement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UserAchievementRepository {
    @Insert("INSERT INTO app_user_achievements (app_user_achievement_id, app_user_id, achievement_id, achieved_at) " +
            "VALUES (#{id}::uuid, #{userId}::uuid, #{achievementId}::uuid, CURRENT_TIMESTAMP) " +
            "ON CONFLICT (app_user_id, achievement_id) DO NOTHING")
    void unlockAchievement(@Param("id") UUID id, @Param("userId") UUID userId, @Param("achievementId") UUID achievementId);

    List<Achievement> getUserAchievementsCurrentUser(Integer offset, Integer size);
}
