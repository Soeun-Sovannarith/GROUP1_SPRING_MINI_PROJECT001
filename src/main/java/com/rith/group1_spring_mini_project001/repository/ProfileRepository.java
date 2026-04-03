package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.model.request.ProfileRequest;
import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface ProfileRepository {

    @Results(id = "UserAppMapper", value = {
            @Result(property = "appUserId",       column = "app_user_id",
                    typeHandler = UUIDTypeHandler.class),
            @Result(property = "username",        column = "username"),
            @Result(property = "email",           column = "email"),
            @Result(property = "password",        column = "password"),
            @Result(property = "level",           column = "level"),
            @Result(property = "xp",              column = "xp"),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified",      column = "is_verified"),
            @Result(property = "createdAt",       column = "created_at")
    })
    @Select("""
        Update app_users set username=#{req.username} , profile_image = #{req.profileImageUrl} where app_user_id= #{appUserId} returning *
    """)
    UserApp updateUserProfile(UUID appUserId, @Param("req") ProfileRequest request);


    @Delete("""
    delete from app_users where app_user_id = #{appUserId};
    """)
    Boolean deleteUserProfile(UUID appUserId);
}