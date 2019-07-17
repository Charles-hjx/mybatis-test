package com.hjx.springbootmybatis.service.serviceImpl;

import com.hjx.springbootmybatis.dao.UserDao;
import com.hjx.springbootmybatis.entity.User;
import com.hjx.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: hjx
 * @Date: 2019/7/8
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public void saveUser(){

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("hjx");
        user.setPhone("123456789");
        user.setPosition("aabhb");
        user.setSex("男");
        user.setPassword("123456");
        userDao.saveUser(user);
    }

    @Override
    public User selectUserById(String id) {
        return userDao.findUserById(id);
    }


}
