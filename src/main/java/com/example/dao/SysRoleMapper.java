package com.example.dao;

import com.example.po.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysRoleMapper {
    /*
    根据auth查询角色,这里auth=id.
     */
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(int id);

    /*
   显示订单,用户管理
    */
    @Select("SELECT * FROM sys_role ")
    SysRole findAll();


}
