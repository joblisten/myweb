package com.example.dao;

import com.example.po.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface SysUserMapper {

    /**
     * 根据name查询用户表
     * @param username
     * @return
     */
    @Select("SELECT id,username,password,auth FROM sys_user WHERE username = #{username}")
    SysUser selectByName(String username);

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */

    @Insert("INSERT INTO sys_user(username,password) VALUES (#{username},#{password})")
    int insertUser(@Param("username") String username, @Param("password") String password);

    /**
     * 荣誉值排行
     * @return
     */
    @Select("SELECT username,honor FROM sys_user ORDER  BY honor DESC  ")
    List<SysUser> selectHonor();

    /**
     * 根据用户id查找用户权限
     * @param id
     * @return
     */
    @Select("SELECT auth FROM sys_user WHERE id = #{id}")
    Integer selectById(Integer id);

    /**
     * 根据id更新荣誉值
     * @param id
     * @param honor
     * @return
     */
    @Update("UPDATE sys_user SET honor=#{honor} WHERE id=#{id}")
    Integer changeHonorById(Integer id,Integer honor);
}
