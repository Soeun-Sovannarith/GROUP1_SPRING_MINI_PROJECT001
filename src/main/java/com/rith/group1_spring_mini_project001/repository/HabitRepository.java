package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {

    @Select("INSERT INTO habits (habit_id, title, description, frequency, is_active, app_user_id, created_at) " +
            "VALUES (#{habit.habitId}::uuid, #{habit.title}, #{habit.description}, #{habit.frequency}::frequency_type, #{habit.isActive}, #{habit.appUserId}::uuid, #{habit.createdAt}) RETURNING *")
    @Results(id = "HabitMapper", value = {
            @Result(property = "habitId", column = "habit_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "frequency", column = "frequency"),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "appUserId", column = "app_user_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "createdAt", column = "created_at")
    })
    Habit save(@Param("habit") Habit habit);

    @Select("SELECT * FROM habits WHERE habit_id = #{habitId}::uuid AND app_user_id = #{userId}::uuid")
    @ResultMap("HabitMapper")
    Habit findByIdAndUserId(@Param("habitId") UUID habitId, @Param("userId") UUID userId);

    @Select("SELECT * FROM habits WHERE app_user_id = #{userId}::uuid ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    @ResultMap("HabitMapper")
    List<Habit> findAllByUserIdWithPagination(@Param("userId") UUID userId, @Param("limit") int limit, @Param("offset") int offset);

    @Select("UPDATE habits SET title = #{habit.title}, description = #{habit.description}, frequency = #{habit.frequency}::frequency_type, is_active = #{habit.isActive} " +
            "WHERE habit_id = #{habit.habitId}::uuid AND app_user_id = #{habit.appUserId}::uuid RETURNING *")
    @ResultMap("HabitMapper")
    Habit update(@Param("habit") Habit habit);

    @Delete("DELETE FROM habits WHERE habit_id = #{habitId}::uuid AND app_user_id = #{userId}::uuid")
    void delete(@Param("habitId") UUID habitId, @Param("userId") UUID userId);

    @Select("SELECT * FROM habits WHERE habit_id = #{habitId}::uuid")
    @ResultMap("HabitMapper")
    Habit findById(@Param("habitId") UUID habitId);
}
