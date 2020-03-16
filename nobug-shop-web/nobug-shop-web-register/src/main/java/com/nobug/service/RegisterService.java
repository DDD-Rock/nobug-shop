package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.boot.context.properties.bind.DefaultValue;

public interface RegisterService {

    ResultBean register(int num, String username, String password,@DefaultValue("") String code);

    ResultBean active(String uuid);

    ResultBean sendSMS(String phoneNum);
}
