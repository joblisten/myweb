package com.example.dao;


import org.apache.ibatis.annotations.Select;

public interface VersionInfoMapper {
    @Select("SELECT version FROM version_info")
    int selectVersion();
}
