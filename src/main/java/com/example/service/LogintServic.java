package com.example.service;

import com.example.po.SysRole;
import com.example.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

/*
自定义登录验证:UserDetailsService实质就是UsernamePasswordAuthenticationFilter
 */
@Slf4j
@Service
public class LogintServic implements UserDetailsService {
    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Collection<GrantedAuthority> list = new ArrayList<>();
        //从数据库读取用户，假设为admin
        SysUser user = userService.selectByName(username);



        if (user != null) {

            SysRole role = roleService.selectById(user.getAutht());  //根据autht，去获取sys_role信息角色ROLE_ADMIN
            list.add(new SimpleGrantedAuthority(role.getRname()));

            log.info("list={}", list);
            return new User(user.getName(), user.getPassword(), list);
        } else
            log.info("没有该用户");

        return new User(user.getName(), user.getPassword(), list);


    }




}