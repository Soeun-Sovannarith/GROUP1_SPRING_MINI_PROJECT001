package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import com.rith.group1_spring_mini_project001.model.model.Achievement;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AchievementRepository {
    @Results(id= "achievementMapper", value ={
            @Result(property = "achievementId", column = "achievement_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "badge", column = "badge"),
            @Result(property = "xpRequired", column = "xp_required")
    })
    @Select("Select * from achievements offset #{offset} limit #{size}")
    List<Achievement> getAchievements(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("SELECT a.* FROM achievements a " +
            "JOIN app_user_achievements aua ON a.achievement_id = aua.achievement_id " +
            "WHERE aua.app_user_id = #{userId} " +
            "ORDER BY aua.achieved_at DESC " +
            "OFFSET #{offset} LIMIT #{size}")
    @ResultMap("achievementMapper")
    List<Achievement> getAchievementsCurrentUser(@Param("userId") UUID userId, @Param("offset") Integer offset, @Param("size") Integer size);
}
