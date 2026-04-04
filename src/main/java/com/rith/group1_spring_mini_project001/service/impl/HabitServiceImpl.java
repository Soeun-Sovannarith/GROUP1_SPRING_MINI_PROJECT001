package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.request.HabitRequest;
import com.rith.group1_spring_mini_project001.model.response.HabitResponse;
import com.rith.group1_spring_mini_project001.repository.HabitRepository;
import com.rith.group1_spring_mini_project001.repository.UserAppRepository;
import com.rith.group1_spring_mini_project001.service.HabitService;
import com.rith.group1_spring_mini_project001.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final UserAppRepository userAppRepository;

    @Override
    public HabitResponse createHabit(HabitRequest habitRequest ) {
        UUID userId = SecurityUtil.getCurrentUser();
        return habitRepository.createHabit(habitRequest, userId);
    }

    @Override
    public List<Habit> getHabitsByCurrentUserId() {
        UUID userId = SecurityUtil.getCurrentUser();
        return habitRepository.findByUserId(userId);
    }

    @Override
    public HabitResponse getHabitById(UUID hId) {
        UUID userId = SecurityUtil.getCurrentUser();
        return habitRepository.getHabitById(hId, userId);
    }

    @Override
    public HabitResponse deleteHabitById(UUID hId) {
        UUID userId = SecurityUtil.getCurrentUser();
        return habitRepository.deleteHabitById(hId, userId);
    }


//    @Override
//    public List<Habit> getHabitsByCurrentUser() {
//        UserApp userApp = (UserApp) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        Long userId = userApp.getCurrentUserId();
//
//        return habitRepository.findByUserId(userId);
//    }
}
