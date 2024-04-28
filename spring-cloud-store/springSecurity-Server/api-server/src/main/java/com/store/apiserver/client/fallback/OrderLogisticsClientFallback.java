package com.store.apiserver.client.fallback;

import com.store.apiserver.client.OrderLogisticsClient;
import com.store.apiserver.model.po.OrderLogistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class OrderLogisticsClientFallback implements FallbackFactory<OrderLogisticsClient> {
    @Override
    public OrderLogisticsClient create(Throwable cause) {
        return id -> {
            log.error("远程调用OrderLogisticsClient#GetOrderLogisticsById方法出现异常，参数：{}", id, cause);
            return null;
        };
    }
}
