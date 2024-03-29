package com.hjx.springbootmybatis.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置  可以配置多个队列
 * @Author: hjx
 * @Date: 2019/7/26
 * @Version 1.0
 */
@Configuration
public class QueueConfig {

    /**
     durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
     auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
     exclusive  表示该消息队列是否只在当前connection生效,默认是false
     */


    @Bean
    public Queue firstQueue(){
        return new Queue("first-queue",true,false,false);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue("second-queue",true,false,false);
    }

    @Bean
    public Queue fan_firstQueue(){
        return new Queue("fan_first-queue",true,false,false);
    }

    @Bean
    public Queue fan_secondQueue() {
        return new Queue("fan_second-queue",true,false,false);
    }

}
