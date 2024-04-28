package com.store.orderserver.config;


import com.store.commen.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MqConstants.STORE_EXCHANGE,true,false);
    }
    //新建队列
    @Bean
    public Queue insertQueue(){
        return new Queue(MqConstants.STORE_INSERT_QUEUE,true);
    }
    @Bean
    public Queue deleteQueue(){
        return new Queue(MqConstants.STORE_DELETE_QUEUE,true);
    }
    //绑定关系
    @Bean
    public Binding insertQueueBinding(){
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(MqConstants.STORE_INSERT_KEY);
    }
    @Bean
    public Binding deleteQueueBinding(){
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(MqConstants.STORE_DELETE_KEY);
    }
}
