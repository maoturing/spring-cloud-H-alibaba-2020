package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author mao  2021/2/26 13:59
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/consul")
    public String paymentZk(HttpServletRequest request) {
        // 获取消费者的 ip
        String ip = request.getRemoteAddr();
//        int serverPort = request.getServerPort();

        // 主要为了学习springcloud, 不再访问数据库, 直接给消费者返回结果
        return "springcloud with consul:" + this.serverPort + "\t 访问者ip: " + ip + "\n" + UUID.randomUUID();
    }

}
