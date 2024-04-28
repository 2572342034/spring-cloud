package com.store.apiserver.client;

import com.store.apiserver.config.DefaultFeignConfig;
import com.store.apiserver.model.po.OrderLogistics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service",contextId = "OrderLogistics",configuration = DefaultFeignConfig.class)
public interface OrderLogisticsClient {
    @GetMapping("/order/GetOrderLogisticsById")
    OrderLogistics GetOrderLogisticsById(@RequestParam("id") String id);
}
