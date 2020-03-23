package com.nobug.controller;

import com.nobug.entity.Message;
import com.nobug.entity.TOrderpay;
import com.nobug.service.TOrderpayService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
@RequestMapping("tOrderpay")

public class TOrderpayController {
    @Resource
    @LoadBalanced
    private RestTemplate restTemplate;

    private final static String Message_URI ="http://NOBUG-SHOP-SERVICE-MQ/mq/";

    @Resource
    private TOrderpayService tOrderpayService;

    /**
     * 通过主键查询单条数据
     *
     *
     * @return 单条数据
     */
    @GetMapping("changePayStatu")
    @RabbitListener(queues = {"nobug_direct_queue"})
    public void  changePayStatu(Message message) {
        try {
            TOrderpay tOrderpay = tOrderpayService.queryById(message.getId());
            tOrderpay.setPaystatus("y");
            tOrderpayService.update(tOrderpay);
            message.setStatus(2);
            restTemplate.getForObject(Message_URI +"rec"+message, Message.class,message);

        }catch (Exception e){

        }
    }
}