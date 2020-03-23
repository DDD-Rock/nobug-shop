package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("NOBUG-SHOP-MAPPER-PRODUCT")
public interface IProductService {

    @RequestMapping("/product/getById")
    ResultBean getById(@RequestParam Integer id);

}
