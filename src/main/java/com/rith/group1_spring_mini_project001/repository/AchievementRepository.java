package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import com.rith.group1_spring_mini_project001.model.model.Achievement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AchievementRepository {
    @Results(id= "achievementMapper", value ={
            @Result(property = "achievementId", column = "achievement_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "badge", column = "badge"),
            @Result(property = "xpRequired", column = "xp_required"),
//            @Result(property = )
    })
    @Select("Select * from achievements offset #{offset} limit #{size}")
    List<Achievement> getAchievements(Integer offset, Integer size);


    List<Achievement> getAchievementsCurrentUser(Integer offset, Integer size);
}
