package com.nobug.controller;

import com.nobug.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("lock")
public class LockController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置分布式锁,五分钟自动解锁
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("setnx")
    public ResultBean setnx(@RequestParam String key, @RequestParam String value) {
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value,5, TimeUnit.MINUTES);

        if (aBoolean) {
            return ResultBean.success();
        } else {
            return ResultBean.error();
        }
    }


    /**
     * 解锁
     * @param key
     * @return
     */
    @RequestMapping("delete")
    public ResultBean delete(@RequestParam String key) {
        Boolean delete = redisTemplate.delete(key);

        if (delete) {
            return ResultBean.success();
        } else {
            return ResultBean.error();
        }
    }

}
