package com.rith.group1_spring_mini_project001.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.RegisterRequest;
import com.rith.group1_spring_mini_project001.repository.UserAppRepository;
import com.rith.group1_spring_mini_project001.service.EmailService;
import com.rith.group1_spring_mini_project001.service.OtpService;
import com.rith.group1_spring_mini_project001.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final UserAppRepository userAppRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    private static final String OTP_PREFIX     = "otp:";
    private static final String PENDING_PREFIX = "pending:";

    @Override
    public void sendRegisterOtp(String email) {
        if (!redisService.hasKey(PENDING_PREFIX + email))
            throw new RuntimeException("No pending registration found for: " + email);

        String otpCode = String.format("%06d", new Random().nextInt(999999));
        redisService.save(OTP_PREFIX + email, otpCode, 5);
        emailService.sendOtp(email, otpCode);
    }

    @Override
    public UserApp verifyRegisterOtp(String email, String otp) {
        Object storedOtp = redisService.get(OTP_PREFIX + email);
        if (storedOtp == null || !storedOtp.toString().equals(otp))
            throw new RuntimeException("Invalid or expired OTP");

        Object pendingObj = redisService.get(PENDING_PREFIX + email);
        if (pendingObj == null)
            throw new RuntimeException("No pending registration found");

        RegisterRequest pending = objectMapper.convertValue(pendingObj, RegisterRequest.class);

        UserApp saved = userAppRepository.save(
                UserApp.builder()
                        .appUserId(UUID.randomUUID())
                        .username(pending.getUsername())
                        .email(pending.getEmail())
                        .password(passwordEncoder.encode(pending.getPassword()))
                        .isVerified(true)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        redisService.delete(OTP_PREFIX + email);
        redisService.delete(PENDING_PREFIX + email);

        return saved;
    }
}