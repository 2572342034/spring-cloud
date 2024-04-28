package com.store.apiserver.client;

import com.store.apiserver.config.DefaultFeignConfig;
import com.store.apiserver.model.po.OrderDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service",contextId = "OrderDetail",configuration = DefaultFeignConfig.class)

public interface OrderDetailClient {
    @GetMapping("/order/GetOrderDetailById")
    OrderDetail GetOrderDetailById(@RequestParam("id") String id);

    @GetMapping("/order/GetOrderDetailListById")
    List<OrderDetail> GetOrderDetailListById(@RequestParam("id") String id);

    @PostMapping("/order/saveOrUpdateOrderDetail")
    void saveOrUpdateOrderDetail(@RequestBody OrderDetail orderDetail);
}
