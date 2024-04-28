package com.store.apiserver.client.fallback;

import com.store.apiserver.client.OrderClient;
import com.store.apiserver.model.po.Order;
import com.store.commen.exception.BizIllegalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Slf4j
public class OrderClientFallback implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable cause) {
        return new OrderClient() {
            @Override
            public Order GetOrderById(String id) {
                log.error("远程调用OrderClient#GetOrderById方法出现异常，参数：{}", id, cause);
                return null;
            }

            @Override
            public List<Order> GetOrderList(Long userid, Integer status) {
                log.error("远程调用OrderClient#GetOrderList方法出现异常，参数：{}", userid, cause);
                return null;
            }

            @Override
            public void saveOrUpdateOrder(@RequestBody Order order) {
                throw new BizIllegalException(cause);
            }
        };
    }
}
