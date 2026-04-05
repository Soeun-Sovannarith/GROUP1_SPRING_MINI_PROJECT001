package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.UserApp;
import com.rith.group1_spring_mini_project001.util.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.UUID;
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
    
    @Select("SELECT * FROM app_users WHERE email = #{identifier} OR username = #{identifier}")
    @ResultMap("UserAppMapper")
    UserApp findByUsernameOrEmail(String identifier);

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

    @Select("""
    INSERT INTO app_users (app_user_id, username, email, password,profile_image, is_verified, created_at)
    VALUES (
        #{user.appUserId}::uuid,
        #{user.displayUsername},
        #{user.email},
        #{user.password},
        #{user.profileImageUrl},
        true,
        now()
    )
    RETURNING *
""")
    @ResultMap("UserAppMapper")
    UserApp save(@Param("user") UserApp user);

    @Select("SELECT COUNT(*) FROM app_users WHERE email = #{email}")
    int existsByEmail(String email);

    @Select("SELECT COUNT(*) FROM app_users WHERE username = #{username}")
    int existsByUsername(String username);

    @Select("SELECT * FROM app_users WHERE app_user_id = #{id}::uuid")
    @ResultMap("UserAppMapper")
    UserApp findById(@Param("id") UUID id);

    @Update("UPDATE app_users SET xp = #{xp}, level = #{level} WHERE app_user_id = #{userId}::uuid")
    void updateXpAndLevel(@Param("xp") Integer xp, @Param("level") Integer level, @Param("userId") UUID userId);
}