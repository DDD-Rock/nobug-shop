package com.nobug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NobugShopWebRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(NobugShopWebRegisterApplication.class, args);
    }

}
