package com.store.apiserver.client.fallback;

import com.store.apiserver.client.ItemClient;
import com.store.apiserver.model.po.Item;
import com.store.commen.exception.BizIllegalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
@Slf4j
public class ItemClientFallback implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public Item queryItemById(Long id) {
                log.error("远程调用ItemClient#queryItemByIds方法出现异常，参数：{}", id, cause);
                return null;
            }

            @Override
            public void saveOrUpdateItem(Item item) {
                throw new BizIllegalException(cause);
            }

            @Override
            public void deteteItemById(Item item) {
                throw new BizIllegalException(cause);
            }


        };
    }
}
