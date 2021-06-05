package com.example.dao;

import com.example.po.Commentary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentaryMapper {


    @Select("SELECT * FROM commentary WHERE bid = #{id}")
    List<Commentary> selectByid(Integer id);
}
