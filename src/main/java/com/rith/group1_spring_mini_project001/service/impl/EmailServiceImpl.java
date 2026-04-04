package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String toEmail, String otpCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Your OTP Verification Code");
            helper.setText(loadTemplate(otpCode), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private String loadTemplate(String otpCode) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/otp-email.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            return html
                    .replace("{{D1}}", String.valueOf(otpCode.charAt(0)))
                    .replace("{{D2}}", String.valueOf(otpCode.charAt(1)))
                    .replace("{{D3}}", String.valueOf(otpCode.charAt(2)))
                    .replace("{{D4}}", String.valueOf(otpCode.charAt(3)))
                    .replace("{{D5}}", String.valueOf(otpCode.charAt(4)))
                    .replace("{{D6}}", String.valueOf(otpCode.charAt(5)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template: " + e.getMessage());
        }
    }
}