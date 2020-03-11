package com.nobug;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.nobug.mapper")
public class NobugShopServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(NobugShopServiceSearchApplication.class, args);
    }

}
