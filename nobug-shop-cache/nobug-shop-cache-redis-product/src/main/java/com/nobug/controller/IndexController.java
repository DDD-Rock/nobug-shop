package com.nobug.controller;

import com.nobug.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("getData")
    public ResultBean getData(@RequestParam String key) {

        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return ResultBean.success(value);
        }
        return ResultBean.error("Redis is empty.");

    }

    @RequestMapping("setData")
    public ResultBean setData(@RequestParam String key, @RequestParam String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            return ResultBean.error("Redis is empty.");
        }
        return ResultBean.success("Save success!");
    }


}
