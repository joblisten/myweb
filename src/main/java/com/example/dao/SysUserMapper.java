package com.example.dao;

import com.example.po.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserMapper {

    /*
    根据id查询用户表
     */
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(int id);


    /*
   根据id查询用户表
    */
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    List<SysUser> selectId(int id);

    /*
    根据name查询用户表
     */

    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);

    /*
    mybaties的insert返回类型默认是int ，账号密码注册
     */
    @Insert("INSERT INTO sys_user(name,password,autht) VALUES (#{username},#{password},2)")
    int setUser(@Param("username") String username, @Param("password") String password);

    /*
    用户管理
     */
    @Select("SELECT * FROM sys_user ")
    List<SysUser> findUser();

    /*
    订单地址，和联系到用户表
     */
    @Update("UPDATE sys_user SET number = #{number},address = #{address}  WHERE  name = #{name}")
    int setNA(@Param("number") String number, @Param("address") String address,@Param("name") String name);


    /*
    根据用户id删除用户
     */
    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    int userde( Integer id);
}
