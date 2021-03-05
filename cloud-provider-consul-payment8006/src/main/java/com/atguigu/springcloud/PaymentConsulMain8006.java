package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *  服务发现, 让注册中心扫描查找到当前服务, 适用于Zookeeper、Eureka、Consul
 *  注册中心zookeeper在yml中配置, 会在应用服务节点cloud-payment-service下注册一个临时节点
 *  如果启动 PaymentMain8005, 也会再注册一个临时节点
 *  /services/cloud-payment-service  根节点/服务节点/实例临时节点
 *
 * @author mao  2021/2/26 13:49
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentConsulMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentConsulMain8006.class, args);
    }
}