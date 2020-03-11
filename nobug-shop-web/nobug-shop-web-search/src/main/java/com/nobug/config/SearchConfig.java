package com.nobug.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SearchConfig {


    //开启负载均衡
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){

        return new RestTemplate();
    }
}
