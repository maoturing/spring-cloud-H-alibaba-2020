package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author mao  2021/2/25 0:43
 */
// 自动向 Eureka 注册, 注册中心在yml中配置
@EnableEurekaClient
// 服务发现, 能够让注册中心查找扫描到该服务, 适用于Zookeeper、Eureka、Consul
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
