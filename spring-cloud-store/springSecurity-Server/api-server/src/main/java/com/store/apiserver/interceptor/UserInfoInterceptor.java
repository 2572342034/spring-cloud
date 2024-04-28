package com.store.apiserver.interceptor;


import com.store.commen.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class UserInfoInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = UserContext.getUser();
        if (userId != null){
            requestTemplate.header("user-info", UserContext.getUser().toString());

        }

    }
}
