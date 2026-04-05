package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.request.HabitRequest;

import java.util.List;
import java.util.UUID;

public interface HabitService {
    Habit createHabit(HabitRequest request, UUID userId);
    Habit getHabitById(UUID habitId, UUID userId);
    List<Habit> getAllHabits(int page, int size, UUID userId);
    Habit updateHabit(UUID habitId, HabitRequest request, UUID userId);
    void deleteHabit(UUID habitId, UUID userId);
}
