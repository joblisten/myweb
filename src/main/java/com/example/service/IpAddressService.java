package com.example.service;

import com.example.dao.IpAddressMapper;
import com.example.po.IpAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class IpAddressService {

    @Autowired
    IpAddressMapper ipAddressMapper;

    /**
     * 添加ip,address
     * @param ip
     * @param address
     * @return
     */

    public boolean insertIpAddress(String ip,String address){
        IpAddress ipAddress = ipAddressMapper.selectByIp(ip);
        //已经有ip不需要添加
        if(ipAddress!=null){
            log.info("该ip={}已经存在",ip);
            return false;
        }
        int row=ipAddressMapper.insertIpAddress(ip,address);
        if (row<=0){
            log.info("ip={}添加失败",ip);
            return false;
        }
        return true;
    }


}
