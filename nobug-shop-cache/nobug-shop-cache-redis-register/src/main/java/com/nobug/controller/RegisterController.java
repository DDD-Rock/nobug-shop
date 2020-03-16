package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.constant.IRegisterConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
@Slf4j
public class RegisterController implements IRegisterConstant {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置redis键值对，用于存放邮箱注册的uuid
     *
     * @param username
     * @param uuid
     * @return
     */
    @RequestMapping("set/uuid/{username}/{uuid}")
    public ResultBean setUUID(@PathVariable String username, @PathVariable String uuid) {

        String key = new StringBuilder().append(IRegisterConstant.REGISTER_UUID).append("=").append(uuid).toString();

        redisTemplate.opsForValue().set(key, username, 15, TimeUnit.MINUTES);

        return ResultBean.success("缓存加入uuid完成");
    }

    /**
     * 删除redis中存放用户注册uuid的键值对
     *
     * @param uuid
     * @return
     */
    @RequestMapping("delete/uuid/{uuid}")
    public ResultBean deleteByKey(@PathVariable String uuid) {

        String key = new StringBuilder().append(IRegisterConstant.REGISTER_UUID).append("=").append(uuid).toString();

        String email = redisTemplate.opsForValue().get(key);
        //如果查无此key
        if (email == null || email.isEmpty()) {
            return ResultBean.error("redis中没有这个key");
        } else {
            Boolean delete = redisTemplate.delete(key);
            if (delete) {
                log.error("redis缓存中删除uuid失败");
            }
            return ResultBean.success(email);
        }
    }

    /**
     * 设置用于手机注册的redis键值对
     *
     * @param phoneNum
     * @param code
     * @return
     */
    @RequestMapping("set/sms/{phoneNum}/{code}")
    public ResultBean setSMS(@PathVariable String phoneNum, @PathVariable String code) {

        String key = new StringBuilder().append(IRegisterConstant.REGISTER_SMS).append("=").append(phoneNum).toString();

        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        return ResultBean.success("缓存加入sms完成");
    }

    /**
     * 校验手机验证码与redis中储存的是否匹配
     *
     * @param phoneNum
     * @param code
     * @return
     */
    @RequestMapping("verify/sms/{phoneNum}/{code}")
    public ResultBean verifySMS(@PathVariable String phoneNum, @PathVariable String code) {
        String key = new StringBuilder().append(IRegisterConstant.REGISTER_SMS).append("=").append(phoneNum).toString();
        String value = redisTemplate.opsForValue().get(key);
        if (code != null && !code.isEmpty() && code.equals(value)) {
            Boolean delete = redisTemplate.delete(key);
            if (!delete) {
                log.error("redis缓存删除失败");
            }
            return ResultBean.success("校验成功");
        } else {
            log.error(String.format("手机号%s校验失败，输入的验证码为%s，实际为%s", phoneNum, code, value));
            return ResultBean.error("校验失败");
        }

    }
}
