package com.example.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class SysUser implements Serializable {
    private Integer id;
    private String username;
    private String password;
    /**
     * 名单
     * 正常,白,黑:0,1,2
     * 默认为0
     */
    private Integer auth;
    /**
     * 荣誉值
     */
    private Integer honor;
}
