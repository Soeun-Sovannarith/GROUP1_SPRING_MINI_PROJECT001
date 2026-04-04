package com.rith.group1_spring_mini_project001.controller;


import com.rith.group1_spring_mini_project001.model.model.Achievement;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.service.AchievementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("api/v1/achievements")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping()
    public ResponseEntity<?> getAchievements(@RequestParam(defaultValue = "1") Integer page, @RequestParam (defaultValue = "10") Integer size) {
        List<Achievement> payload = achievementService.getAchievements(page, size);
        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Achievements fetched successfully")
                .data(payload)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{app-users}")
    public ResponseEntity<?> getAchievementsCurrentUser(@RequestParam(defaultValue = "1") Integer page, @RequestParam (defaultValue = "10") Integer size) {
        List<Achievement> payload = achievementService.getAchievementsCurrentUser(page, size);
        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Achievements fetched successfully")
                .data(payload)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.ok().body(response);
     }

}
