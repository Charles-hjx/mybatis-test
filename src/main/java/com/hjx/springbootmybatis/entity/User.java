package com.hjx.springbootmybatis.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Author: hjx
 * @Date: 2019/7/8
 * @Version 1.0
 */
@Data
public class User implements UserDetails {

    private String id;

    private  String name;

    private String password;

    private String sex;

    private String position;

    private String phone;

    private List<UserGrantedAuthority> UserGrantedAuthorityList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.UserGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
