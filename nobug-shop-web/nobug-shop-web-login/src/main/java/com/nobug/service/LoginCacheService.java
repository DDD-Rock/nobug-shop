package com.nobug.service;

import com.nobug.ResultBean;
import com.nobug.dto.UserDTO;
import com.nobug.service.fallback.LoginCacheServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nobug-shop-cache-redis-login",fallback = LoginCacheServiceHystrix.class)
public interface LoginCacheService {

    @RequestMapping("/redis/get/userInfo")
    ResultBean getUserInfoByKey(@RequestParam String redisKey);

    @RequestMapping(value = "/redis/set/userInfo",method = RequestMethod.POST,consumes = "application/json")
    ResultBean setUserInfo(@RequestParam String redisKey,@RequestBody UserDTO userDTO);
}
