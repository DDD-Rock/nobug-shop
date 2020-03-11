package com.nobug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NobugShopMapperUserInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NobugShopMapperUserInfoApplication.class, args);
    }

}
