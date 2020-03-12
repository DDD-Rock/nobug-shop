package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.constant.IRegisterConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
public class RegisterController implements IRegisterConstant {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("set/uuid/{username}/{uuid}")
    public ResultBean setUUID(@PathVariable String username, @PathVariable String uuid) {

        String key = new StringBuilder().append(IRegisterConstant.REGISTER_UUID).append("=").append(uuid).toString();

        redisTemplate.opsForValue().set(key, username, 15, TimeUnit.MINUTES);

        return ResultBean.success("缓存加入完成");
    }

    @RequestMapping("delete/{uuid}")
    public ResultBean deleteByKey(@PathVariable String uuid) {

        String key = new StringBuilder().append(IRegisterConstant.REGISTER_UUID).append("=").append(uuid).toString();

        String email = redisTemplate.opsForValue().get(key);
        //如果查无此key
        if (email == null || email.isEmpty()) {
            return ResultBean.error("redis中没有这个key");
        } else {
            Boolean delete = redisTemplate.delete(key);
            if (delete){
                return ResultBean.success(email);
            }else{
                return ResultBean.error("删除失败");
            }


        }


    }

}
