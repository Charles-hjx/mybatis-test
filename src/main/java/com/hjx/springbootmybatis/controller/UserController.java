package com.hjx.springbootmybatis.controller;

import com.hjx.springbootmybatis.entity.User;
import com.hjx.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hjx
 * @Date: 2019/7/8
 * @Version 1.0
 */
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/save")
    public int saveUser(){
        userService.saveUser();
        return 1;
    }

    @RequestMapping("/find/{id}")
    public User selectById(@PathVariable("id") String id){
        return userService.selectUserById(id);
    }
}
