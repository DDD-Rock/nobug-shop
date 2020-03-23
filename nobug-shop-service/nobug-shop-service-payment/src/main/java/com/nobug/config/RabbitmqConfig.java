package com.nobug.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    /**
    1.创建队列
    2.创建交换机
    3.将队列绑定在交换机上
     */

    @Bean
    public Queue getQueue(){
        return new Queue("nobug_direct_queue");
    }

    //创建一个路由模式的交换机
    @Bean
    public DirectExchange getDirectExchange() {
        return new DirectExchange("nobug_direct_exchange");
    }
    @Bean
    public Binding getBinding(Queue queue,DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("com.nobug");
    }








}
