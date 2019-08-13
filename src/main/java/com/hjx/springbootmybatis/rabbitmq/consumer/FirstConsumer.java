package com.hjx.springbootmybatis.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 没有加 componet
 *  .amqp.rabbit.listener.exception.FatalListenerExecutionException: No message listener specified - see property 'messageListener' 没有
 *
 * @Author: hjx
 * @Date: 2019/7/26
 * @Version 1.0
 */
@Component
public class FirstConsumer {

    @RabbitListener(queues = {"first-queue","second-queue"},containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message){
        System.out.println("FirstConsumer {} handleMessage :"+message);
    }

    @RabbitListener(queues = "fan_first-queue" ,containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void receive(String message){
        System.out.println("fan_first-queue {} receive ===>>>>"+message);
    }

    @RabbitListener(queues = "fan_second-queue" ,containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void receive2(String message){
        System.out.println("fan_first-queue {} receive2 ===>>>>"+message);
    }
}
