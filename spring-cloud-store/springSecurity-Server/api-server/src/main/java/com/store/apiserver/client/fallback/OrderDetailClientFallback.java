package com.store.apiserver.client.fallback;

import com.store.apiserver.client.OrderDetailClient;
import com.store.apiserver.model.po.OrderDetail;
import com.store.commen.exception.BizIllegalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
public class OrderDetailClientFallback implements FallbackFactory<OrderDetailClient> {
    @Override
    public OrderDetailClient create(Throwable cause) {
        return new OrderDetailClient() {
            @Override
            public OrderDetail GetOrderDetailById(String id) {
                log.error("远程调用OrderDetailClient#GetOrderDetailById方法出现异常，参数：{}", id, cause);
                return null;
            }

            @Override
            public List<OrderDetail> GetOrderDetailListById(String id) {
                log.error("远程调用OrderDetailClient#GetOrderDetailListById方法出现异常，参数：{}", id, cause);
                return null;
            }

            @Override
            public void saveOrUpdateOrderDetail(@RequestBody OrderDetail orderDetail) {
                throw new BizIllegalException(cause);
            }
        };
    }
}
