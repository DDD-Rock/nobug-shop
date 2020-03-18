package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
@Slf4j
public class LoginController {

    //    @Autowired  //这个注解会注入失败，需要用@Resource，详见百度二者的区别
    @Resource
    public RedisTemplate<String, UserDTO> redisTemplate;

    @RequestMapping("get/userInfo")
    public ResultBean getUserInfoByKey(@RequestParam String redisKey) {

        UserDTO userDTO = redisTemplate.opsForValue().get(redisKey);
        if (userDTO == null) {
            log.debug("redis中没有登录信息");
            return ResultBean.error("redis中没有登录信息");
        }

        return ResultBean.success(userDTO);
    }

    @RequestMapping(value = "/set/userInfo",method = RequestMethod.POST)
    public ResultBean setUserInfo(@RequestParam String redisKey, @RequestBody UserDTO userDTO) {
        try {
            redisTemplate.opsForValue().set(redisKey, userDTO,24, TimeUnit.HOURS);
        } catch (Exception e) {
            log.debug("setUserInfo写入redis遇到了未知的问题");
            return ResultBean.error("写入redis遇到了未知的问题");
        }
        return ResultBean.success("用户信息成功写入redis");
    }

}
