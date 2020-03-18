package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class LoginController {

//    @Resource
    @Autowired
    public RedisTemplate<String,UserDTO> redisTemplate;

    @RequestMapping("get/userInfo")
    public ResultBean getUserInfoByKey(String redisKey){

        UserDTO userDTO = redisTemplate.opsForValue().get(redisKey);
        if (userDTO==null){
            return ResultBean.error("redis中没有登录信息");
        }

        return ResultBean.success(userDTO);
    }

    @RequestMapping("set/userInfo")
    public ResultBean setUserInfo(String redisKey, UserDTO userDTO){
        try {
            redisTemplate.opsForValue().set(redisKey,userDTO);
        } catch (Exception e) {
            return ResultBean.error("写入redis遇到了未知的问题");
        }
        return ResultBean.success("用户信息成功写入redis");
    }

}
