package com.store.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 更安全的做法是明确列出允许的源，而不是使用 "*"
        // 例如，允许来自 http://example.com 和 https://another-example.com 的请求
        config.addAllowedOrigin("http://localhost:5173/");
        config.addAllowedOrigin("http://localhost/");
        // 如果你的前端应用部署在多个源上，可以继续添加
        // config.addAllowedOrigin("...");

        // 允许所有请求方法
        config.addAllowedMethod("*");
        // 允许所有头部信息
        config.addAllowedHeader("*");
        // 允许携带cookie
        config.setAllowCredentials(true);

//        // 配置前端请求可能会携带的自定义头部信息
//        config.addExposedHeader("hasInitialCleanupPerformed");
        config.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有路径生效
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}