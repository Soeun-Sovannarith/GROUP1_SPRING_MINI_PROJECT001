package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.request.HabitRequest;
import com.rith.group1_spring_mini_project001.model.response.HabitResponse;
import com.rith.group1_spring_mini_project001.repository.HabitRepository;

import java.util.List;
import java.util.UUID;

public interface HabitService {
    HabitResponse createHabit(HabitRequest habitRequest);
    List<Habit> getHabitsByCurrentUserId();

    HabitResponse getHabitById(UUID hId);

    HabitResponse deleteHabitById(UUID hId);
}
