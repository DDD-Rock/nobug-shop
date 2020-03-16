package com.nobug.controller;

import com.nobug.ResultBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class SendSMSController {

    @RequestMapping("send/{phoneNum}/{code}")
    public ResultBean sendSMS(@PathVariable String phoneNum, @PathVariable String code) {
        //TODO 发送短信的业务代码

        return ResultBean.success("sms发送成功");
    }

}
