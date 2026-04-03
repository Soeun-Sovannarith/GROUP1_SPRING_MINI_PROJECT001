package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;
@Mapper
public interface UserAppRepository {

    @Select("SELECT * FROM app_users WHERE email = #{email}")
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
//            @Result(property = "otp",             column = "otp"),
            @Result(property = "createdAt",       column = "created_at")
    })
    UserApp findByEmail(String email);

    @Update("""
        UPDATE app_users
        SET is_verified = #{user.isVerified}
        WHERE otp = #{user.otp}
    """)
    void update(@Param("user") UserApp user);

    @Select("""
        UPDATE app_users
        SET otp = #{user.otp},
            created_at = #{user.createdAt}
        WHERE email = #{user.email}
        RETURNING *
    """)
    @ResultMap("UserAppMapper")
    UserApp updateOtp(@Param("user") UserApp user);
}