package com.store.itemserver;

import com.store.apiserver.config.DefaultFeignConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients(basePackages = "com.store.apiserver.client", defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
@MapperScan("com.store.itemserver.mapper")
public class ItemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemServerApplication.class, args);
    }
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.142.101:9200")
        ));
    }
}
