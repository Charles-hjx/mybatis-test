package com.hjx.springbootmybatis.rabbitmq.producer;

import com.hjx.springbootmybatis.config.rabbitmq.ExchangeConfig;
import com.hjx.springbootmybatis.config.rabbitmq.RabbitMqConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送 生产者1
 * @Author: hjx
 * @Date: 2019/7/26
 * @Version 1.0
 */
@Component
public class FirstSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String uuid,Object message){
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DIR,RabbitMqConfig.ROUTEINGKEY_1,
                message, correlationId);
    }

    public void send_fan(String uuid,Object message){
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_FAN,"",message,correlationId);
    }

}
