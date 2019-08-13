package com.hjx.springbootmybatis.config.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq配置
 * @Author: hjx
 * @Date: 2019/7/26
 * @Version 1.0
 */
@Configuration
public class RabbitMqConfig {

    /** 消息交换机的名字*/
    public static final String EXCHANGE_DIR = "dir_hjx";
    public static final String EXCHANGE_TOPIC = "topic_hjx";
    public static final String EXCHANGE_FAN = "fanout_hjx";
    public static final String ROUTEINGKEY_1 = "queue_one_key1";
    public static final String ROUTEINGKEY_2 = "queue_one_key2";


    @Autowired
    private QueueConfig queueConfig;
    @Autowired
    private ExchangeConfig exchangeConfig;

    @Autowired
    ConnectionFactory connectionFactory;

    /**
     * 将消息队列1和交换机进行绑定  如果将消息发送到一个没有队列绑定的exchange上面，那么该消息将会丢失，
     * 这是因为在rabbitMQ中exchange不具备存储消息的能力，只有队列具备存储消息的能力。
     * @return
     */
    @Bean
    public Binding binding_one(){
        return BindingBuilder.bind(queueConfig.firstQueue())
                .to(exchangeConfig.directExchange())//为 firstQueue 绑定相应的 交换机
                .with(RabbitMqConfig.ROUTEINGKEY_1);//为改queue 声明一个key
    }

    /**
     * 将消息队列2和交换机进行绑定
     */

    @Bean
    public Binding binding_two(){
        return BindingBuilder.bind(queueConfig.secondQueue())
                .to(exchangeConfig.directExchange()).with(RabbitMqConfig.ROUTEINGKEY_2);
    }

    //++++++++++++++fanout 模式绑定
    @Bean
    public Binding fan_binding_one(){
        //fanout模式会将消息 存入所有绑定的queue中 所以不需要指定key
        return  BindingBuilder.bind(queueConfig.fan_firstQueue()).to(exchangeConfig.fanoutExchange());
    }

    @Bean
    public Binding fan_binding_two(){
        //fanout模式会将消息 存入所有绑定的queue中 所以不需要指定key
        return  BindingBuilder.bind(queueConfig.fan_secondQueue()).to(exchangeConfig.fanoutExchange());
    }

    //++++++++++++++fanout 模式绑定

    /**
     * queue listener 观察 监听模式
     * 当有消息到达时会通知监听在对应的队列上的监听对象
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.addQueues(getQueues());
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        return simpleMessageListenerContainer;
    }

    /**
     * 监听的队列
     * @return
     */
    private Queue[] getQueues(){
        return new Queue[]{
                queueConfig.firstQueue(),
                queueConfig.fan_firstQueue(),
                queueConfig.fan_secondQueue()
        };
    }

    /**
     * 定义rabbit template用于数据的接收和发送
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());
        //template.setReturnCallback(msgSendReturnCallback());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        //  template.setMandatory(true);


        return template;
    }

    @Bean
    public  MsgSendConfirmCallBack msgSendConfirmCallBack() {

        return new MsgSendConfirmCallBack();
    }





}


