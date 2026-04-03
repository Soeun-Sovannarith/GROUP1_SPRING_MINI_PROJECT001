package com.rith.group1_spring_mini_project001.service;

import com.rith.group1_spring_mini_project001.model.request.ProfileRequest;
import com.rith.group1_spring_mini_project001.model.response.ProfileResponse;
import jakarta.validation.Valid;

public interface ProfileService {
    ProfileResponse getUserProfile();


    ProfileResponse updateUserProfile(@Valid ProfileRequest request);

    Boolean deleteUserProfile();
}
