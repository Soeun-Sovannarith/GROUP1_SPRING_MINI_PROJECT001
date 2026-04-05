package com.rith.group1_spring_mini_project001.util;

import com.rith.group1_spring_mini_project001.model.model.UserApp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {
    public static UUID getCurrentUser() {
        UserApp user = (UserApp) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return user.getAppUserId();
    }
}
