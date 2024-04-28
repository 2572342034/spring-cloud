package com.store.apiserver.client;

import com.store.apiserver.config.DefaultFeignConfig;
import com.store.apiserver.model.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service",configuration = DefaultFeignConfig.class)
public interface UserClient {
    @GetMapping("/user/selectUserById")
    User SelectUserById(@RequestParam("id") String id);
    @PostMapping("/user/saveOrUpdateUser")
    void saveOrUpdateUser(@RequestBody User user);
}
