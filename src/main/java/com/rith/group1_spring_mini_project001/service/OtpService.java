package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.model.UserApp;

public interface OtpService {
    void sendRegisterOtp(String email);
    UserApp verifyRegisterOtp(String email, String otp);
}