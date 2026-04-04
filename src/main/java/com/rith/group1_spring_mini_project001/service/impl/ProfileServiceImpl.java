package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.ProfileRequest;
import com.rith.group1_spring_mini_project001.model.response.ProfileResponse;
import com.rith.group1_spring_mini_project001.repository.ProfileRepository;
import com.rith.group1_spring_mini_project001.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProfileResponse getUserProfile() {
        UserApp userApp = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ProfileResponse.builder()
                .appUserId(userApp.getAppUserId())
                .username(userApp.getDisplayUsername()) // real profile username
                .email(userApp.getEmail())
                .level(userApp.getLevel())
                .xp(userApp.getXp())
                .profileImageUrl(userApp.getProfileImageUrl())
                .isVerified(userApp.getIsVerified())
                .createdAt(userApp.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public ProfileResponse updateUserProfile(ProfileRequest request) {
        UserApp userAppOld = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserApp userAppNew = profileRepository.updateUserProfile(userAppOld.getAppUserId(), request);

        return ProfileResponse.builder()
                .appUserId(userAppNew.getAppUserId())
                .username(userAppNew.getDisplayUsername()) // real profile username
                .email(userAppNew.getEmail())
                .level(userAppNew.getLevel())
                .xp(userAppNew.getXp())
                .profileImageUrl(userAppNew.getProfileImageUrl())
                .isVerified(userAppNew.getIsVerified())
                .createdAt(userAppNew.getCreatedAt())
                .build();
    }


    @Override
    public Boolean deleteUserProfile() {
        UserApp userAppOld = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean isDelete = profileRepository.deleteUserProfile(userAppOld.getAppUserId());
        return isDelete;
    }

}
