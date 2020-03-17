package com.nobug.service;

import com.nobug.ResultBean;
import com.nobug.service.fallback.LoginServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "nobug-shop-mapper-user-info",fallback = LoginServiceHystrix.class)
public interface LoginService {

    @RequestMapping("/login/user")
    ResultBean userLogin(@RequestParam String flag, @RequestParam String username);

}
