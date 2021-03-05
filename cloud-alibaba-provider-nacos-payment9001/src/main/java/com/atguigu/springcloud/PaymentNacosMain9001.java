package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mao  2021/3/4 14:54
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentNacosMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentNacosMain9001.class, args);
    }
}
