package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.HabitLog;
import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {

    @Select("INSERT INTO habit_logs (habit_log_id, status, xp_earned, habit_id, created_at) " +
            "VALUES (#{log.habitLogId}::uuid, #{log.status}, #{log.xpEarned}, #{log.habitId}::uuid, #{log.createdAt}) RETURNING *")
    @Results(id = "HabitLogMapper", value = {
            @Result(property = "habitLogId", column = "habit_log_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "status", column = "status"),
            @Result(property = "xpEarned", column = "xp_earned"),
            @Result(property = "habitId", column = "habit_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "createdAt", column = "created_at")
    })
    HabitLog save(@Param("log") HabitLog log);

    @Select("SELECT * FROM habit_logs WHERE habit_id = #{habitId}::uuid ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    @ResultMap("HabitLogMapper")
    List<HabitLog> findByHabitIdWithPagination(@Param("habitId") UUID habitId, @Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT EXISTS(SELECT 1 FROM habit_logs WHERE habit_id = #{habitId}::uuid AND created_at::date = CURRENT_DATE)")
    boolean hasLoggedToday(@Param("habitId") UUID habitId);
}
