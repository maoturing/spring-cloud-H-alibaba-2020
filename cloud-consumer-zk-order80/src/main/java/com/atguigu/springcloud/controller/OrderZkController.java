package com.atguigu.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author mao  2021/2/25 2:09
 */
@RestController
public class OrderZkController {

    // 自动去zk注册中心去查找PAYMENT服务, 调用PAYMENT服务8004
    public static final String PAYMENT_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zk")
    public String paymentInfo() {
        String result = restTemplate.getForObject(PAYMENT_URL + "/payment/zk", String.class);
        return result;
    }
}
