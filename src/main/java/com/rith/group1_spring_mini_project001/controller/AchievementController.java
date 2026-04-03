package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.model.model.Achievement;
import com.rith.group1_spring_mini_project001.model.response.AchievementLoginResponse;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;
    @Operation(summary = "Get all achievements")
    @GetMapping
    public ResponseEntity<AchievementLoginResponse<List<Achievement>>> getAllAchievements(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<Achievement> achievements = achievementService.getAllAchievements(page, size);
        {
            AchievementLoginResponse<List<Achievement>> achievementLoginResponse = AchievementLoginResponse.<List<Achievement>>builder().success(true).status(HttpStatus.OK).message("Achievements retrieved successfully!").payload(achievements).timestamp(Instant.now()).build();
            ResponseEntity.status(HttpStatus.OK).body(achievementLoginResponse);
            return ResponseEntity.status(HttpStatus.OK).body(achievementLoginResponse);
        }
    }

}
