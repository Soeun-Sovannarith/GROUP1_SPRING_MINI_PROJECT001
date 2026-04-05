package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.jwt.JwtService;
import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.AuthLoginRequest;
import com.rith.group1_spring_mini_project001.model.request.RegisterRequest;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.model.response.AuthLoginResponse;
import com.rith.group1_spring_mini_project001.repository.UserAppRepository;
import com.rith.group1_spring_mini_project001.service.OtpService;
import com.rith.group1_spring_mini_project001.service.RedisService;
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
    private final OtpService otpService;
    private final RedisService redisService;
    private final UserAppRepository userAppRepository;
    private static final String PENDING_PREFIX = "pending:";

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthLoginResponse>> login(
            @RequestBody AuthLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getIdentifier(),
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
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @RequestBody RegisterRequest request) {

        if (userAppRepository.existsByEmail(request.getEmail()) > 0)
            throw new RuntimeException("Email already exists");
        if (userAppRepository.existsByUsername(request.getUsername()) > 0)
            throw new RuntimeException("Username already exists");

        redisService.save(PENDING_PREFIX + request.getEmail(), request, 10);
        otpService.sendRegisterOtp(request.getEmail());

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("OTP sent to " + request.getEmail() + ". Please verify your OTP to complete registration.")
                .data(null)
                .timestamp(Instant.now())
                .build());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        otpService.verifyRegisterOtp(email, otp);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Account created successfully. You can now login.")
                .timestamp(Instant.now())
                .build());
    }

    // resend OTP
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<Void>> resendOtp(
            @RequestParam String email) {

        otpService.sendRegisterOtp(email);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("OTP resent to " + email)
                .data(null)
                .timestamp(Instant.now())
                .build());
    }
}