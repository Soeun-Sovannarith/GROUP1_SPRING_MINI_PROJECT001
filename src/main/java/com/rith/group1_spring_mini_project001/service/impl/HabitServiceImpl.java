package com.rith.group1_spring_mini_project001.service.impl;


import com.rith.group1_spring_mini_project001.repository.HabitRepository;
import com.rith.group1_spring_mini_project001.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    public final HabitRepository habitRepository;
}
