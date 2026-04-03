package com.rith.group1_spring_mini_project001.service.impl;


import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.repository.HabitRepository;
import com.rith.group1_spring_mini_project001.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    public final HabitRepository habitRepository;

    @Override
    public List<Habit> getAllHabit(int page, int size) {
        Integer offset = size * (page - 1);
        return habitRepository.getAllHabits(offset, size);
    }
}
