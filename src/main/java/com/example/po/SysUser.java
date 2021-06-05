package com.example.po;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;


@Data
public class SysUser implements Serializable {
    static  final  long serialVersiouUID=1L;
    private Integer id;
    private String name;
    private String  password;
    private Integer autht;  //角色
    private String number;
    private String address;

    /*
    implements  UserDeils

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    ////账户是否过期,过期无法验证
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    //指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    //指示是否已过期的用户的凭据(密码),过期的凭据防止认证
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    //是否被禁用,禁用的用户不能身份验证
    @Override
    public boolean isEnabled() {
        return false;
    }*/
}
