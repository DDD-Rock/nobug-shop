package com.nobug.service.impl;

import com.nobug.entity.Message;
import com.nobug.entity.TOrderpay;
import com.nobug.service.TOrderpayService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AnnotationQuartz {

    @Resource
    private TOrderpayService tOrderpayService;



    @Scheduled(cron = "0/30 * * * * ?")
    public void processInitMsg(Message message) {
        //TODO 回调消息发送服务中心接口用消息业务id查找对应的业务数据是否生成，如果生成则直接将消息改成已发送状态并投递消息到mq
        MessageServiceImpl messageService = new MessageServiceImpl();
        String s = messageService.messageHashMap.get(message.getId());
        if(s.equals("init")){
            //TODO 查询订单状态是否为已支付
            TOrderpay tOrderpay = tOrderpayService.queryById(message.getId());
            String paystatus = tOrderpay.getPaystatus();
            if(paystatus.equals("y")){
                //将消息状态设置为1
                message.setStatus(1);
                //发送消息
                new MessageServiceImpl().sendMsg(message);
            }
        }
    }

    @Scheduled(cron = "0/60 * * * * ?")
    public void processSentMsg(Message message) {
        //TODO 定时处理未完成的消息，如果已经几次查找消息都还是处于未完成状态，则很可能消息没有被消费者接收成功，由于消费者有幂等性实现，则可重发消息给mq
    }
}
