package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.jwt.JwtService;
import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.AuthLoginRequest;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.model.response.AuthLoginResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthLoginResponse>> login(
            @RequestBody AuthLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserApp userApp = (UserApp) authentication.getPrincipal();
        String token = jwtService.generateToken(userApp);

        return ResponseEntity.ok(
                ApiResponse.<AuthLoginResponse>builder()
                        .success(true)
                        .status(HttpStatus.OK)
                        .message("Login successful")
                        .data(AuthLoginResponse.builder()
                                .userId(userApp.getAppUserId())
                                .token(token)
                                .email(userApp.getEmail())
                                .username(userApp.getDisplayUsername())
                                .build())
                        .timestamp(Instant.now())
                        .build()
        );
    }
}