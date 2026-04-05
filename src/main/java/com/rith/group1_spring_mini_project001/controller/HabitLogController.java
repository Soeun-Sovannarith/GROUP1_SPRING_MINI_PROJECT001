package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.model.model.HabitLog;
import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.HabitLogRequest;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.service.HabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/habit-logs")
@RequiredArgsConstructor
public class HabitLogController {

    private final HabitLogService habitLogService;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserApp user = (UserApp) auth.getPrincipal();
        return user.getAppUserId();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HabitLog>> createHabitLog(@RequestBody HabitLogRequest request) {
        HabitLog log = habitLogService.createHabitLog(request, getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<HabitLog>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Habit log created successfully")
                .data(log)
                .timestamp(Instant.now())
                .build());
    }

    @GetMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<List<HabitLog>>> getHabitLogs(
            @PathVariable("habit-id") UUID habitId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<HabitLog> logs = habitLogService.getHabitLogs(habitId, page, size, getCurrentUserId());
        return ResponseEntity.ok(ApiResponse.<List<HabitLog>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Habit logs fetched successfully")
                .data(logs)
                .timestamp(Instant.now())
                .build());
    }
}
