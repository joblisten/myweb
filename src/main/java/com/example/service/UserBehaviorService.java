package com.example.service;

import com.example.dao.IpAddressMapper;
import com.example.dao.UserBehaviorMapper;
import com.example.po.IpAddress;
import com.example.util.IpAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@Service
public class UserBehaviorService {
    @Autowired
    UserBehaviorMapper userBehaviorMapper;

    @Autowired
    IpAddressMapper ipAddressMapper;

    @Autowired
    IpAddressService ipAddressService;

    /**
     * 添加行为
     * @param userid
     * @param ip
     * @param event
     * @param content
     * @return
     */
    public boolean insertBehavior(Integer userid, String ip, String event, String content){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MMM-ddd kk:mm");
        String format = simpleDateFormat.format(new Date());
        Date date = null;
        try {
            date = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        IpAddress ipAddress = ipAddressMapper.selectByIp(ip);

        //如果ip表,ip已经存在,直接拿ip对应的地址,添加到行为表
        if(ipAddress!=null){
            //添加行为
            return insert(userid,ip,ipAddress.getAddress(),event,content,date);
        }

        //否则根据ip接口获取,地址
        String address=IpAddressUtil.getCityInfo(ip);

        if (address==null){
            log.info("ip={}");
            return false;
        }
        //把新ip对应地址添加进去
        ipAddressService.insertIpAddress(ip,address);
        return  insert(userid,ip,address,event,content,date);
    }


    public boolean insert(Integer userid, String ip, String address, String event, String content, Date date){
        int row=userBehaviorMapper.insertBehavior(userid,ip,address,event,content,date);
        if (row<=0){
            log.info("行为添加失败");
            return false;
        }
        log.info("行为添加成功");
        return true;
    }

}
