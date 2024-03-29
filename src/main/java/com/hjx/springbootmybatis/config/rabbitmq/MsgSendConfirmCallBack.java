package com.hjx.springbootmybatis.config.rabbitmq;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 *
 * @Author: hjx
 * @Date: 2019/7/26
 * @Version 1.0
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback{

    /**
     * 消息发送到交换机确认机制
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("MsgSendConfirmCallBack  , 回调id:" + correlationData);
        if(ack){
            System.out.println("消息消费成功");
        }else{
            System.out.println("消息消费失败:" + cause+"\n重新发送");
        }
    }
}

