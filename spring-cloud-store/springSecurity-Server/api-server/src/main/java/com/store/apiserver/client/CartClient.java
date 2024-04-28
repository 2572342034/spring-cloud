package com.store.apiserver.client;

import com.store.apiserver.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cart-service",configuration = DefaultFeignConfig.class)
public interface CartClient {
    @GetMapping("/cart/DeleteCartById")
    void DeleteCartById(@RequestParam(value = "id") Long id);
}
