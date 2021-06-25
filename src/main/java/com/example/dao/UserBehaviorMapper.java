package com.example.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserBehaviorMapper {

    @Insert("INSERT INTO user_behavior (userid,ip,address,event,content,date) VALUES (#{userid},#{ip},#{address},#{event},#{content},#{date})")
    int insertBehavior(@Param("userid") Integer userid, @Param("ip")String ip,
                       @Param("address")String address, @Param("event")String event,
                       @Param("content")String content, @Param("date") Date date);
}
