package com.store.addressserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.store.addressserver.mapper")
public class AddressServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressServerApplication.class, args);
    }

}
