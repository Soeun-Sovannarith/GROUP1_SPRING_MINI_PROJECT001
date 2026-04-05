package com.rith.group1_spring_mini_project001.controller;


import com.rith.group1_spring_mini_project001.model.model.Achievement;
import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.service.AchievementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserApp user = (UserApp) auth.getPrincipal();
        return user.getAppUserId();
    }

    @GetMapping("app-user")
    public ResponseEntity<?> getAchievementsCurrentUser(@RequestParam(defaultValue = "1") Integer page, @RequestParam (defaultValue = "10") Integer size) {
        List<Achievement> payload = achievementService.getAchievementsCurrentUser(getCurrentUserId(), page, size);
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
