package com.hjx.springbootmybatis.service;

import com.hjx.springbootmybatis.entity.User;

/**
 * @Author: hjx
 * @Date: 2019/7/9
 * @Version 1.0
 */
public interface UserService {

    void saveUser();
    User selectUserById(String id);

}
