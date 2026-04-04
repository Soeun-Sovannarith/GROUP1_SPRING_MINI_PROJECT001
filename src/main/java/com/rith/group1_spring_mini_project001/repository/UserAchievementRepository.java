package com.rith.group1_spring_mini_project001.repository;

import com.rith.group1_spring_mini_project001.model.model.Achievement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAchievementRepository {


    List<Achievement> getgetUserAchievementsCurrentUser(Integer offset, Integer size);
}
