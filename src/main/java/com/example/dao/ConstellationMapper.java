package com.example.dao;

import com.example.po.Constellation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConstellationMapper {
    /*
    根据星座，获取星座，描述猫咪性格
     */
    @Select("SELECT * FROM con WHERE name = #{name}")
    List<Constellation> selectByName(@Param("name")String name);




}
