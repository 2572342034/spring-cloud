package com.store.apiserver.client.fallback;

import com.store.apiserver.client.UserClient;
import com.store.apiserver.model.dto.User;
import com.store.commen.exception.BizIllegalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class UserClientFallback implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public User SelectUserById(String id) {
                log.error("远程调用UserClient#SelectUserById方法出现异常，参数：{}", id, cause);
                return null;
            }

            @Override
            public void saveOrUpdateUser(User user) {
                throw new BizIllegalException(cause);
            }
        };
    }
}
