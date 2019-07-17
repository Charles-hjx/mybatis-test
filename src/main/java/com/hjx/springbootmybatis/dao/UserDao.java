package com.hjx.springbootmybatis.dao;

import com.hjx.springbootmybatis.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: hjx
 * @Date: 2019/7/8
 * @Version 1.0
 */
@Repository
public interface UserDao {

   User findUserById(String id);

   int saveUser(User user);

}
