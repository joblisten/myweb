package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
注册服务
 */
@Service
public class RegisterService {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;

    public int regis(String username,String password){

        int user=sysUserService.setUser(username,password); //添加数据

        return user;
    }

}
