package com.nobug.service.impl;

import com.nobug.ResultBean;
import com.nobug.service.RegisterService;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Override
    public ResultBean register(int num, String username, String password, String code) {


        if (num == 1) {
            //email注册
            //1.将账号密码存入数据库

            //2.发送激活邮件


        } else if (num == 2) {
            //手机号注册


        }


        return null;
    }

}
