package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.repository.UserAppRepository;
import com.rith.group1_spring_mini_project001.service.UserAppService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService {

    private final UserAppRepository userAppRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = userAppRepository.findByEmail(email);
        System.out.println("USER FOUND: " + user);
        System.out.println("IS VERIFIED: " + user.getIsVerified());
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return user;
    }
}