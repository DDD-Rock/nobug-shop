package com.nobug.service;

import com.nobug.ResultBean;
import com.nobug.UserDTO;
import com.nobug.service.fallback.LoginCacheServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(path = "nobug-shop-cache-redis-login",fallback = LoginCacheServiceHystrix.class)
public interface LoginCacheService {

    @RequestMapping("get/userInfo")
    ResultBean getUserInfoByKey(String redisKey);

    @RequestMapping("set/userInfo")
    ResultBean setUserInfo(String redisKey, UserDTO userDTO);
}
