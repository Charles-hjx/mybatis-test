package com.hjx.springbootmybatis.controller;

import com.hjx.springbootmybatis.rabbitmq.producer.FirstSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: hjx
 * @Date: 2019/7/26
 * @Version 1.0
 */
@RestController
@RequestMapping("/mq")
public class TestRabbitmqController {

    @Autowired
    FirstSender firstSender;

    @GetMapping("/send")
    public  String send(String message){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        firstSender.send(uuid,message);
        return uuid;
    }

    @GetMapping("/send/fan")
    public  String send2(String message){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        firstSender.send_fan(uuid,message);
        return uuid;
    }
}
