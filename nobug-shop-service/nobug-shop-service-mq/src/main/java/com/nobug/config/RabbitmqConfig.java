package com.nobug.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {


    //路由模式交换机
    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange("nobug_direct_exchange");
    }
    //通配符交换机
    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange("nobug_topic_exchange",true,false);
    }


}
