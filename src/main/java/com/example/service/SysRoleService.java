package com.example.service;

import com.example.dao.SysRoleMapper;
import com.example.po.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    public  SysRole selectById(int id){
        return roleMapper.findAll();
    }

    public SysRole selectById(Integer id){
        return roleMapper.selectById(id);
    }
}
