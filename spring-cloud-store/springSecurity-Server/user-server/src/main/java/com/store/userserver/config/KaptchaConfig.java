package com.store.userserver.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties props = new Properties();
        // 设置Kaptcha的基本属性
        props.setProperty("kaptcha.image.width", "90"); // 设置图片宽度
        props.setProperty("kaptcha.image.height", "40"); // 设置图片高度
        props.setProperty("kaptcha.textproducer.font.names", "Arial, Courier");
        // 设置Kaptcha的基本属性，如边框、文字、颜色、字体等

        // 设置验证码字体大小
        props.setProperty("kaptcha.textproducer.font.size", "30"); // 这里设置字体大小为40像素
//        props.setProperty("kaptcha.border", "yes");
        props.setProperty("kaptcha.textproducer.char.length", "4");
        props.setProperty("kaptcha.noise.count", "0"); // 关闭噪音点
        props.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy"); // 或选择无扭曲的实现
        props.setProperty("kaptcha.background.clear.from", "white");
        props.setProperty("kaptcha.background.clear.to", "gray");
        props.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        // 更多配置...
        Config config = new Config(props);

        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}