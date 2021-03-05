package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author mao  2021/3/2 8:51
 */
@SpringBootApplication
@EnableEurekaClient     // 网关微服务也要注册
public class GatewayMian9527 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMian9527.class, args);
    }
}
