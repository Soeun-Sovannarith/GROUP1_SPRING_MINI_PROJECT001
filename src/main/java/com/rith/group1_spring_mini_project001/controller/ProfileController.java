package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.model.request.ProfileRequest;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.model.response.ProfileResponse;
import com.rith.group1_spring_mini_project001.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> getUserProfile(){
        ProfileResponse userApp = profileService.getUserProfile();
        ApiResponse<ProfileResponse> response = ApiResponse.<ProfileResponse>builder().status(HttpStatus.OK).message("User profile fetched successfully!").data(userApp).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> updateUserProfile(@RequestBody @Valid ProfileRequest request){
        ProfileResponse userApp = profileService.updateUserProfile(request);
        ApiResponse<ProfileResponse> response = ApiResponse.<ProfileResponse>builder().status(HttpStatus.OK).message("User profile updated successfully!").data(userApp).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Boolean>> deleteUserProfile(){
        profileService.deleteUserProfile();
        ApiResponse<Boolean> response = ApiResponse.<Boolean>builder().status(HttpStatus.OK).message("User profile deleted successfully!").data(null).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
