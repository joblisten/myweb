package com.example.service;

import com.example.dao.SysUserMapper;
import com.example.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper userMapper;

    /*
   根据用户id删除用户
    */

    public int userde( Integer id){
        return userMapper.userde(id);
    }

    /*
    支付地址等信息
     */
    public List<SysUser> selectId(int id){
        return userMapper.selectId(id);
    }
    /*
    用户管理
     */
    public List<SysUser> findUser(){
        return userMapper.findUser();
    }
    /*
    用户表的地址和信息
     */
    public int setNA( String number, String address, String name){
        return userMapper.setNA(number,address,name);
    }
    public SysUser selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public SysUser selectByName(String name) { return userMapper.selectByName(name);
    }

    public int setUser(String username, String password) {
        return userMapper.setUser(username,password);
    }
}
