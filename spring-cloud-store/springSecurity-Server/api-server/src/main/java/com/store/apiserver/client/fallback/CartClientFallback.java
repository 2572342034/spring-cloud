package com.store.apiserver.client.fallback;

import com.store.apiserver.client.CartClient;
import com.store.commen.exception.BizIllegalException;
import org.springframework.cloud.openfeign.FallbackFactory;

public class CartClientFallback implements FallbackFactory<CartClient> {
    @Override
    public CartClient create(Throwable cause) {
        return id -> {
            throw new BizIllegalException(cause);
        };
    }
}
