package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.model.request.HabitRequest;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.model.response.HabitResponse;
import com.rith.group1_spring_mini_project001.service.HabitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/habits")
public class HabitController {
    private final HabitService habitService;

    @PostMapping
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ApiResponse<HabitResponse>> createHabit(@RequestBody HabitRequest habitRequest){
        HabitResponse createHabit = habitService.createHabit(habitRequest);
        ApiResponse<HabitResponse> response = ApiResponse.<HabitResponse>builder().success(true).status(HttpStatus.CREATED).message("").data(createHabit).timestamp(Instant.now()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<ApiResponse<HabitResponse>> getHabitById(@PathVariable("habitId") UUID hId){
        HabitResponse getHabitById = habitService.getHabitById(hId);
        return ResponseEntity.ok(
                ApiResponse.<HabitResponse>builder().success(true).status(HttpStatus.OK).message("").data(getHabitById).timestamp(Instant.now()).build()
        );
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<ApiResponse<HabitResponse>> deleteHabitById(@PathVariable("habitId") UUID hId){
        HabitResponse deleteHabitById = habitService.deleteHabitById(hId);
        return ResponseEntity.ok(
                ApiResponse.<HabitResponse>builder().success(true).status(HttpStatus.OK).message("").data(deleteHabitById).timestamp(Instant.now()).build()
        );
    }
}
