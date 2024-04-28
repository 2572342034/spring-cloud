package com.store.cartserver;

import com.store.apiserver.client.ItemClient;
import com.store.apiserver.config.DefaultFeignConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootTest

@Slf4j
class CartServerApplicationTests {


    @Test
    void contextLoads() {
    }

}
