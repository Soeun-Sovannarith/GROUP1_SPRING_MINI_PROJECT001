package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.exception.ResourceNotFoundException;
import com.rith.group1_spring_mini_project001.model.model.Achievement;
import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.model.HabitLog;
import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.HabitLogRequest;
import com.rith.group1_spring_mini_project001.repository.*;
import com.rith.group1_spring_mini_project001.service.HabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitLogServiceImpl implements HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;
    private final UserAppRepository userAppRepository;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Override
    public HabitLog createHabitLog(HabitLogRequest request, UUID userId) {
        Habit habit = habitRepository.findByIdAndUserId(request.getHabitId(), userId);
        if (habit == null) throw new ResourceNotFoundException("Habit with ID " + request.getHabitId() + " not found");

        if (habitLogRepository.hasLoggedToday(request.getHabitId())) {
            throw new IllegalArgumentException("You have already logged this habit today. Come back tomorrow!");
        }

        int xpEarned = 10;
        HabitLog log = HabitLog.builder()
                .habitLogId(UUID.randomUUID())
                .status(request.getStatus())
                .xpEarned(xpEarned)
                .habitId(request.getHabitId())
                .createdAt(LocalDateTime.now())
                .build();

        HabitLog savedLog = habitLogRepository.save(log);

        if ("Completed".equalsIgnoreCase(request.getStatus())) {
            UserApp user = userAppRepository.findById(userId);
            int currentXp = user.getXp() != null ? user.getXp() : 0;
            int newXp = currentXp + xpEarned;
            int newLevel = Math.max(1, newXp / 100);

            user.setXp(newXp);
            user.setLevel(newLevel);
            userAppRepository.updateXpAndLevel(newXp, newLevel, userId);

            // Unlock achievements
            List<Achievement> unlockable = achievementRepository.findUnlockableAchievements(newXp, userId);
            for (Achievement ach : unlockable) {
                userAchievementRepository.unlockAchievement(UUID.randomUUID(), userId, ach.getAchievementId());
            }
        }

        return savedLog;
    }

    @Override
    public List<HabitLog> getHabitLogs(UUID habitId, int page, int size, UUID userId) {
        if (habitRepository.findByIdAndUserId(habitId, userId) == null) {
            throw new ResourceNotFoundException("Habit with ID " + habitId + " not found");
        }
        int offset = (page - 1) * size;
        return habitLogRepository.findByHabitIdWithPagination(habitId, size, offset);
    }
}
