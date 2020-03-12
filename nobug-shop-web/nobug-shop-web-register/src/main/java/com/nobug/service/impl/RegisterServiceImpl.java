package com.nobug.service.impl;

import com.nobug.ResultBean;
import com.nobug.UserDTO;
import com.nobug.service.RegisterService;
import com.nobug.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ResultBean register(int num, String username, String password, String code) {


        if (num == 1) {
            //email注册
            UserDTO userDTO = new UserDTO();
            userDTO.setUname(username);
            userDTO.setPassword(password);
            userDTO.setFlag(0);
            //1.将邮箱和密码存入数据库
            ResultBean resultBean = restTemplate.postForObject("http://NOBUG-SHOP-MAPPER-USER-INFO/mapper/email", userDTO, ResultBean.class);
            System.out.println(resultBean.getMessage());
            //2.将用户激活邮件的邮箱和uuid存到redis，等待验证，15分钟有效期
            String uuid = UUID.randomUUID().toString();
            String set_redis_uri=new StringBuilder().append("http://nobug-shop-cache-redis-register/redis/set/uuid/").append(username).append("/").append(uuid).toString();
            ResultBean resultBean1 = restTemplate.getForObject(set_redis_uri, ResultBean.class);
            System.out.println(resultBean1.getMessage());
            //3.发送激活邮件
            String send_email_uri=new StringBuilder().append("http://nobug-shop-service-send-mail/email/send/").append(username).append("/").append(uuid).toString();
            ResultBean resultBean2 = restTemplate.getForObject(send_email_uri, ResultBean.class);
            System.out.println(resultBean2.getMessage());

        } else if (num == 2) {
            //手机号注册


        }


        return ResultBean.success("注册成功");
    }

}
