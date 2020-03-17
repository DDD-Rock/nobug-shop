package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
@Slf4j
public class SendSMSController {

    @RequestMapping("send/{phoneNum}/{code}")
    public ResultBean sendSMS(@PathVariable String phoneNum, @PathVariable String code) {

        String smsResult = SmsUtil.sendTplSms("b4f40f061acd466c9acbb8f0092913ff", phoneNum, "@1@=" + code, "");

        log.info(smsResult);

        return ResultBean.success("sms发送成功");
    }

}
