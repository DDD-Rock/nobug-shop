package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient
public interface IndexMapperService {

    ResultBean getProductType();
}
