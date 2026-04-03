package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.model.model.Habit;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.metadata.PostgresTableMetaDataProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/habit/v1")
@RequiredArgsConstructor
public class HabitController {
    public final HabitService habitService;

    @GetMapping("/{getAll}")
    public ResponseEntity<?> getAllHabit(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        List<Habit> getAllHabit = habitService.getAllHabit(page, size);
        ApiResponse<List<Habit>> response = ApiResponse.<List<Habit>>builder()
                .status(HttpStatus.OK)
                .message("Get all data successfully")
                .data(getAllHabit).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}