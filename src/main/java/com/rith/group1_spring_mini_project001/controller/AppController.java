package com.rith.group1_spring_mini_project001.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app")
@SecurityRequirement(name = "bearerAuth")
public class AppController {
    @GetMapping
    public String app() {
        return "Hello World";
    }


}
