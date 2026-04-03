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

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProfileResponse getUserProfile() {
        UserApp userApp = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileResponse userAppResponse = modelMapper.map(userApp, ProfileResponse.class);

        return userAppResponse;
    }

    @Override
    public ProfileResponse updateUserProfile(ProfileRequest request) {
        UserApp userAppOld = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserApp userAppNew = profileRepository.updateUserProfile(userAppOld.getAppUserId(),request);
        ProfileResponse userAppResponse = modelMapper.map(userAppNew, ProfileResponse.class);
        return userAppResponse;
    }

    @Override
    public Boolean deleteUserProfile() {
        UserApp userAppOld = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean isDelete = profileRepository.deleteUserProfile(userAppOld.getAppUserId());
        return isDelete;
    }

}
