package com.nobug.controller;

import com.nobug.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件接口
 *
 * @Author DDD-Rock
 *
 */
@RestController
@RequestMapping("email")
public class SendMailController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${activeServerUrl}")
    private String activeServerUrl;

    @RequestMapping("send/{address}/{uuid}")
    public ResultBean sendEmail(@PathVariable String address,
                                @PathVariable String uuid){
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper messageHelper=null;
        try {
            messageHelper=new MimeMessageHelper(message,true);
            messageHelper.setSubject("[nobug-shop]请激活您在本站注册的账号");
            //读取模板内容
            Context context =new Context();
            context.setVariable("username",address.substring(0,address.lastIndexOf('@')));
            context.setVariable("url",activeServerUrl+uuid);

            String info = templateEngine.process("mail_model", context);
            //将模板内容放入邮件中
            messageHelper.setText(info,true);

            messageHelper.setFrom("964443622@qq.com");
            messageHelper.setTo(address);

            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return ResultBean.success("邮件发送成功！");
    }

}
