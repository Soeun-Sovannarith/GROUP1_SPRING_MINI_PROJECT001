package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.request.HabitRequest;
import com.rith.group1_spring_mini_project001.model.response.HabitResponse;
import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {

    @Results(id = "HabitMapper", value = {
            @Result(property = "habitId", column = "habit_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "frequency", column = "frequency"),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "appUserId", column = "app_user_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "createAt", column = "create_at")
    })
    @Select("""
            
            INSERT INTO habits (habit_id, title, description, frequency, is_active, app_user_id, created_at)
                   VALUES (#{req.habitId}, #{req.title}, #{req.description},\s
                           #{req.frequency}::frequency_type, #{req.isActive},\s
                           #{req.appUserId}::uuid, #{req.createdAt})
                   RETURNING *
            """)
    HabitResponse createHabit(@Param("req") HabitRequest habitRequest, @Param("userId") UUID userId);

    @Select("SELECT * FROM habits WHERE habit_id = #{hId} AND app_useer_id = #{userId}")
    @ResultMap("HabitMapper")
    HabitResponse getHabitById(@Param("hId") UUID hId, @Param("userId") UUID userId);

    @ResultMap("HabitMapper")
    @Select(
            """
                    SELECT  * FROM habits WHERE app_user_id = #{userId}
                    """
    )
    List<Habit> findByUserId(@Param("userId") UUID userId);


    @ResultMap("HabitMapper")
    @Select(
            """
                    DELETE FROM habits WHERE habit_id = #{hId} AND app_user_id = #{userId} 
                    """
    )
    HabitResponse deleteHabitById(@Param("hId") UUID hId, @Param("userId") UUID userId);
}
