package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.Achievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AchievementRepository {
    @Results(id = "achievementMapper", value={
            @Result(property = "achievementId", column = "achievement_id"),
            @Result(property = "achievementTitle", column = "title"),
            @Result(property = "achievementDescription", column = "description"),
            @Result(property = "xpRequired", column = "xp_required"),
    })
    @Select("""
    SELECT * FROM achievements OFFSET #{offset} LIMIT #{size};
""")
    List<Achievement> getAllAchievements(int offset, Integer size);
}
