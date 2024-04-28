package com.store.apiserver.client;

import com.store.apiserver.config.DefaultFeignConfig;
import com.store.apiserver.model.po.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service",contextId = "Order",configuration = DefaultFeignConfig.class)
public interface OrderClient {
    @GetMapping("/order/GetOrderById")
    Order GetOrderById(@RequestParam("id") String id);
    @GetMapping("/order/GetOrderList")
    List<Order> GetOrderList(@RequestParam("userid") Long userid, @RequestParam("status") Integer status);
    @PostMapping("/order/saveOrUpdateOrder")
    void saveOrUpdateOrder(@RequestBody Order order);
}
