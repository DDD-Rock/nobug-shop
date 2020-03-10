package com.nobug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NobugShopWebOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NobugShopWebOrderApplication.class, args);
    }

}
