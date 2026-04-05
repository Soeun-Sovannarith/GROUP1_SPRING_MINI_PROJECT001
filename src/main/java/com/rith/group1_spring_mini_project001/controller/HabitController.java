package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.HabitRequest;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.model.response.AppUserResponse;
import com.rith.group1_spring_mini_project001.model.response.HabitResponse;
import com.rith.group1_spring_mini_project001.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserApp user = (UserApp) auth.getPrincipal();
        return user.getAppUserId();
    }

    private UserApp getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserApp) auth.getPrincipal();
    }

    private HabitResponse toHabitResponse(Habit habit, UserApp user) {
        AppUserResponse userResponse = AppUserResponse.builder()
                .appUserId(user.getAppUserId())
                .username(user.getDisplayUsername())
                .email(user.getEmail())
                .level(user.getLevel())
                .xp(user.getXp())
                .profileImageUrl(user.getProfileImageUrl())
                .isVerified(user.getIsVerified())
                .createdAt(user.getCreatedAt())
                .build();

        return HabitResponse.builder()
                .habitId(habit.getHabitId())
                .title(habit.getTitle())
                .description(habit.getDescription())
                .frequency(habit.getFrequency() != null ? habit.getFrequency().toUpperCase() : null)
                .isActive(habit.getIsActive())
                .appUserResponse(userResponse)
                .createdAt(habit.getCreatedAt())
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HabitResponse>> createHabit(@RequestBody HabitRequest request) {
        Habit habit = habitService.createHabit(request, getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<HabitResponse>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Habit created successfully!")
                .data(toHabitResponse(habit, getCurrentUser()))
                .timestamp(Instant.now())
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HabitResponse>>> getAllHabits(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        UserApp currentUser = getCurrentUser();
        List<HabitResponse> habits = habitService.getAllHabits(page, size, currentUser.getAppUserId())
                .stream()
                .map(habit -> toHabitResponse(habit, currentUser))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.<List<HabitResponse>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Habits fetched successfully")
                .data(habits)
                .timestamp(Instant.now())
                .build());
    }

    @GetMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<HabitResponse>> getHabitById(@PathVariable("habit-id") UUID habitId) {
        Habit habit = habitService.getHabitById(habitId, getCurrentUserId());
        return ResponseEntity.ok(ApiResponse.<HabitResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Habit fetched successfully")
                .data(toHabitResponse(habit, getCurrentUser()))
                .timestamp(Instant.now())
                .build());
    }

    @PutMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<HabitResponse>> updateHabit(@PathVariable("habit-id") UUID habitId, @RequestBody HabitRequest request) {
        Habit habit = habitService.updateHabit(habitId, request, getCurrentUserId());
        return ResponseEntity.ok(ApiResponse.<HabitResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Habit updated successfully")
                .data(toHabitResponse(habit, getCurrentUser()))
                .timestamp(Instant.now())
                .build());
    }

    @DeleteMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<Void>> deleteHabit(@PathVariable("habit-id") UUID habitId) {
        habitService.deleteHabit(habitId, getCurrentUserId());
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Habit deleted successfully")
                .data(null)
                .timestamp(Instant.now())
                .build());
    }
}
