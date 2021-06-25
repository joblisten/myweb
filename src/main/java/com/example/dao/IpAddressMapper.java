package com.example.dao;

import com.example.po.IpAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface IpAddressMapper {

    @Insert("INSERT INTO ip_address (ip,address) VALUES (#{ip},#{address})")
    int insertIpAddress(@Param("ip") String ip,@Param("address") String address);

    @Select("SELECT * FROM ip_address WHERE ip=#{ip}")
    IpAddress selectByIp(@Param("ip") String ip);
}
