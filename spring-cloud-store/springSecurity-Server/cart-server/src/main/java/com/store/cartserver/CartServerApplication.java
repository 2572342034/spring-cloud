package com.store.cartserver;

import com.store.apiserver.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.store.cartserver.mapper")
@EnableFeignClients(basePackages = "com.store.apiserver.client", defaultConfiguration = DefaultFeignConfig.class)
public class CartServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServerApplication.class, args);
    }

}
