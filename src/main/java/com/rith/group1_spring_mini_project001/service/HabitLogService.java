package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.model.HabitLog;
import com.rith.group1_spring_mini_project001.model.request.HabitLogRequest;

import java.util.List;
import java.util.UUID;

public interface HabitLogService {
    HabitLog createHabitLog(HabitLogRequest request, UUID userId);
    List<HabitLog> getHabitLogs(UUID habitId, int page, int size, UUID userId);
}
