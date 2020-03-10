package com.nobug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NobugShopServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(NobugShopServiceSearchApplication.class, args);
    }

}
