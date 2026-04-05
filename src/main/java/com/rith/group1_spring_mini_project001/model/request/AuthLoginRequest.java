package com.rith.group1_spring_mini_project001.model.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequest {
    private String identifier;
    private String password;
}