package com.rith.group1_spring_mini_project001.service;

public interface EmailService {
    void sendOtp(String toEmail, String otpCode);
}