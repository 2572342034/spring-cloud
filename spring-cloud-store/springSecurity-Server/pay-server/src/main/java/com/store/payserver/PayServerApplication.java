package com.store.payserver;

import com.store.apiserver.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.store.apiserver.client", defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
@MapperScan("com.store.payserver.mapper")
public class PayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayServerApplication.class, args);
    }

}
