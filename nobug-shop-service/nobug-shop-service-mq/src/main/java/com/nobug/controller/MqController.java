package com.nobug.controller;

import com.nobug.entity.Message;
import com.nobug.service.MessageService;
import com.nobug.service.impl.AnnotationQuartz;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


@Controller("/mq")
public class MqController {

    @Resource
    private MessageService messageService;

    @Resource
    private AnnotationQuartz annotationQuartz;

    @RequestMapping("/rec")
    public void recMessage(Message message) {
        messageService.receiveMsg(message);

    }

    @RequestMapping("/send")
    public void sebdMessage(Message message) {
        messageService.sendMsg(message);

    }

}
