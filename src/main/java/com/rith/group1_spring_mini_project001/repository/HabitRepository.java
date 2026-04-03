package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HabitRepository {

    @Results(id = "habitMapper", value = {
            @Result(property = "habitId", column = "habit_id"),
            @Result(property = "appUserId", column = "app_user_id"),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
            SELECT *
            FROM habits
            ORDER BY created_at DESC
            LIMIT #{size} OFFSET #{offset}
            """)
    List<Habit> getAllHabits(@Param("offset") int offset, @Param("size") int size);

}
