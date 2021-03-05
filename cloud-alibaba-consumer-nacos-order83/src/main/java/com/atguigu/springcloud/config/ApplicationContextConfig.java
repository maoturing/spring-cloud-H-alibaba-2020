package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author mao  2021/3/4 15:21
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced       // 不配置会报错 UnknownHostException: nacos-payment-provider
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
