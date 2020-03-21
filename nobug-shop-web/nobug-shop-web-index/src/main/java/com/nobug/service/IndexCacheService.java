package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 从分布式缓存中存取数据
 */
@FeignClient(value = "NOBUG-SHOP-CACHE-REDIS-PRODUCT")
public interface IndexCacheService {

    /**
     * 取kv
     * @param key
     * @return
     */
    @RequestMapping("/index/getData")
    ResultBean getData(@RequestParam String key);

    /**
     * 设置kv
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/index/setData")
    ResultBean setData(@RequestParam String key, @RequestParam String value);


    /**
     * 设置分布式锁
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/lock/setnx")
    ResultBean setnx(@RequestParam String key, @RequestParam String value);

    /**
     * 解锁
     * @param key
     * @return
     */
    @RequestMapping("/lock/delete")
    ResultBean delete(@RequestParam String key);

}
