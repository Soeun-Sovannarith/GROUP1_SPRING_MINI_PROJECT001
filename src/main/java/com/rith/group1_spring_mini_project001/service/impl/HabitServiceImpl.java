package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.exception.ResourceNotFoundException;
import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.request.HabitRequest;
import com.rith.group1_spring_mini_project001.repository.HabitRepository;
import com.rith.group1_spring_mini_project001.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    @Override
    public Habit createHabit(HabitRequest request, UUID userId) {
        String freq = request.getFrequency() != null ? request.getFrequency().toLower() : "";
        if (!freq.equals("daily") && !freq.equals("weekly") && !freq.equals("monthly")) {
            throw new IllegalArgumentException("Frequency must be 'daily', 'weekly', or 'monthly'");
        }

        Habit habit = Habit.builder()
                .habitId(UUID.randomUUID())
                .title(request.getTitle())
                .description(request.getDescription())
                .frequency(freq)
                .isActive(true)
                .appUserId(userId)
                .createdAt(LocalDateTime.now())
                .build();
        return habitRepository.save(habit);
    }

    @Override
    public Habit getHabitById(UUID habitId, UUID userId) {
        Habit habit = habitRepository.findByIdAndUserId(habitId, userId);
        if (habit == null) throw new ResourceNotFoundException("Habit with ID " + habitId + " not found");
        return habit;
    }

    @Override
    public List<Habit> getAllHabits(int page, int size, UUID userId) {
        int offset = (page - 1) * size;
        return habitRepository.findAllByUserIdWithPagination(userId, size, offset);
    }

    @Override
    public Habit updateHabit(UUID habitId, HabitRequest request, UUID userId) {
        Habit existing = getHabitById(habitId, userId);

        String freq = request.getFrequency() != null ? request.getFrequency().toLower() : "";
        if (!freq.equals("daily") && !freq.equals("weekly") && !freq.equals("monthly")) {
            throw new IllegalArgumentException("Frequency must be 'daily', 'weekly', or 'monthly'");
        }

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setFrequency(freq);
        return habitRepository.update(existing);
    }

    @Override
    public void deleteHabit(UUID habitId, UUID userId) {
        getHabitById(habitId, userId); // verify exists
        habitRepository.delete(habitId, userId);
    }
}
