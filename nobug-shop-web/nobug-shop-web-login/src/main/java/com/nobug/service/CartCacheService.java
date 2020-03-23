package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nobug-shop-cache-redis-cart")
public interface CartCacheService {

    @RequestMapping("/cart/merge")
    ResultBean merge(@RequestParam String uuid, @RequestParam String user_id);
}
