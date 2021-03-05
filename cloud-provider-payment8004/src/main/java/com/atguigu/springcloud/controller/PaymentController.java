package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author mao  2021/2/26 13:59
 */
@RestController
@Slf4j
public class PaymentController {


    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/zk")
    public String paymentZk() {
        // 主要为了学习springcloud, 不再访问数据库
        return "springcloud with zookeeper:" + serverPort + "\t" + UUID.randomUUID();
    }

}
