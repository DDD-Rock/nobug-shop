package com.nobug.service.impl;

import com.nobug.entity.Message;
import com.nobug.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.nobug.constant.MqConstant.*;

/**
 * 独立消息服务
 *
 */
@Service(value = "messageServiceImpl")
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    HashMap<Integer , String> messageHashMap = new HashMap<>();

    @Override
    @Transactional
    public void receiveMsg(Message message) {
        if (message == null) {
            throw new RuntimeException("消息为空");
        }


        switch (message.getStatus()) {
            case MessageStatus_INIT:
                // TODO save msg
                //将该消息存入hashMap
                messageHashMap.put(message.getId(),"init");
                log.info(message.toString()+"保存初始消息");
                break;
            case MessageStatus_SENT:
                // update msg status 为sent
               if(messageHashMap.containsKey(message.getId())) {
                   messageHashMap.put(message.getId(),"sent");
               }
                // 发送消息到订单减库存队列
                sendMsg(message);
                log.info("更新消息状态为sent");
                break;
            case MessageStatus_END:
                // TODO update msg status 为end
                if(messageHashMap.containsKey(message.getId())) {
                    messageHashMap.remove(message.getId());
                }

                break;

            default:
                throw new RuntimeException("消息状态有误");
        }
    }

    @Override
    public void sendMsg(Message message) {
        rabbitTemplate.convertAndSend("nobug_direct_exchange","com.nobug",message);
    }

}
