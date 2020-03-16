package com.nobug.service.impl;

import com.nobug.ResultBean;
import com.nobug.UserDTO;
import com.nobug.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
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
            userDTO.setEmail(username);
            userDTO.setPassword(password);
            userDTO.setFlag(0);
            userDTO.setCreateTime(new Date());
            //1.将邮箱和密码存入数据库
            ResultBean resultBean = restTemplate.postForObject("http://NOBUG-SHOP-MAPPER-USER-INFO/mapper/email", userDTO, ResultBean.class);
            System.out.println(resultBean.getMessage());
            //2.将用户激活邮件的邮箱和uuid存到redis，等待验证，15分钟有效期
            String uuid = UUID.randomUUID().toString();
            String set_redis_uri = new StringBuilder().append("http://nobug-shop-cache-redis-register/redis/set/uuid/").append(username).append("/").append(uuid).append("/").toString();
            ResultBean resultBean1 = restTemplate.getForObject(set_redis_uri, ResultBean.class);
            System.out.println(resultBean1.getMessage());
            //3.发送激活邮件
            String send_email_uri = new StringBuilder().append("http://nobug-shop-service-send-mail/email/send/").append(username).append("/").append(uuid).append("/").toString();
            ResultBean resultBean2 = restTemplate.getForObject(send_email_uri, ResultBean.class);
            System.out.println(resultBean2.getMessage());

        } else if (num == 2) {
            //手机号注册
            //1.核对redis中的验证码和用户输入的是否一致。
            String set_redis_uri = new StringBuilder().append("http://nobug-shop-cache-redis-register/redis/verify/sms/").append(username).append("/").append(code).append("/").toString();
            ResultBean resultBean = restTemplate.getForObject(set_redis_uri, ResultBean.class);
            if (resultBean.getErrno() == 0) {
                //2.将用户注册信息存入数据库。
                UserDTO userDTO = new UserDTO();
                userDTO.setPhone(username);
                userDTO.setPassword(password);
                userDTO.setFlag(1);//直接已激活状态
                userDTO.setCreateTime(new Date());
                ResultBean mysqlResultBean = restTemplate.postForObject("http://NOBUG-SHOP-MAPPER-USER-INFO/mapper/email", userDTO, ResultBean.class);
                System.out.println(mysqlResultBean.getMessage());
            } else {
                return resultBean;
            }


        }

        return ResultBean.success("注册成功");
    }

    /**
     * 激活email注册的账号
     *
     * @param uuid
     * @return
     */
    @Override
    public ResultBean active(String uuid) {

        //redis处理（删除对应的key-value）
        String key = "http://nobug-shop-cache-redis-register/redis/delete/uuid/" + uuid;
        ResultBean resultBean = restTemplate.getForObject(key, ResultBean.class);
        if (resultBean.getErrno() == 0) {
            //删除成功，处理数据库操作
            ResultBean mysqlResultBean = restTemplate.getForObject("http://nobug-shop-mapper-user-info/mapper/active/" + resultBean.getMessage(), ResultBean.class);
            return mysqlResultBean;
        } else {
            //删除失败
            return ResultBean.error("您的激活链接已过期，请重新申请链接！");
        }

    }

    /**
     * @param phoneNum
     * @return
     */
    @Override
    public ResultBean sendSMS(String phoneNum) {
        //1.生成一个随机的验证码
        String sms_code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        //2.发送验证码，存入redis
        String send_sms_uri = new StringBuilder().append("http://nobug-shop-service-send-sms/sms/send/").append(phoneNum).append("/").append(sms_code).append("/").toString();
        ResultBean resultBean = restTemplate.getForObject(send_sms_uri, ResultBean.class);
        return resultBean;
    }
}
