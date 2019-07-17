package com.hjx.springbootmybatis.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @Author: hjx
 * @Date: 2019/7/8
 * @Version 1.0
 */
@Data
public class UserGrantedAuthority implements GrantedAuthority {

    String grantedAuthority;

    List<User> userList;

    @Override
    public String getAuthority() {
        return this.grantedAuthority;
    }
}
