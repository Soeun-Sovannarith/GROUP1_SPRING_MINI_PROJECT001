package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.model.Habit;

import java.util.List;

public interface HabitService {
    List<Habit> getAllHabit(int page, int size);
}
