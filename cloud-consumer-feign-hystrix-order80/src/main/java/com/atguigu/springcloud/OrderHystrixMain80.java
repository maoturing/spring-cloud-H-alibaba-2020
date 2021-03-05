package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author mao  2021/2/25 2:07
 */
// 开启OpenFeign
@EnableFeignClients
// 开启Hystrix, @EnableHystrix 底层也是@EnableCircuitBreaker
@EnableCircuitBreaker
@SpringBootApplication
public class OrderHystrixMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain80.class, args);
    }
}
