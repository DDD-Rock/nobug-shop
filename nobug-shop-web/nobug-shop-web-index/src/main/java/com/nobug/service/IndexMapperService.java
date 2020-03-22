package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "NOBUG-SHOP-MAPPER-PRODUCT")
public interface IndexMapperService {

    @RequestMapping("/index/getProductType")
    ResultBean getProductType();
}
