package com.rith.group1_spring_mini_project001.model.response;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginResponse {
    private UUID userId;
    private String token;
    private String email;
    private String username;
}