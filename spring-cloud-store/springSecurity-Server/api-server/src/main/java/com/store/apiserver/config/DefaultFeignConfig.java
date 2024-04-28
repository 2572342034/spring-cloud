package com.store.apiserver.config;


import com.store.apiserver.client.fallback.*;
import com.store.apiserver.interceptor.UserInfoInterceptor;
import feign.Logger;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
    @Bean
    public RequestInterceptor userInfoInterceptor(){
        return new UserInfoInterceptor();
    }
    @Bean
    public ItemClientFallback itemClientFallback(){
        return new ItemClientFallback();
    }
    @Bean
    public CartClientFallback cartClientFallback(){
        return new CartClientFallback();
    }
    @Bean
    OrderClientFallback orderClientFallback(){
        return new OrderClientFallback();
    }
    @Bean
    OrderDetailClientFallback orderDetailClientFallback(){
        return new OrderDetailClientFallback();
    }
    @Bean
    OrderLogisticsClientFallback orderLogisticsClientFallback(){
        return new OrderLogisticsClientFallback();
    }
    @Bean
    UserClientFallback userClientFallback(){
        return new UserClientFallback();
    }
}

