package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mao  2021/2/28 3:02
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    /**
     * paymentInfoTimeout 在收到高并发请求时, 会占用系统资源,
     * 导致能够正常响应的请求也变慢, 拖垮整个系统
     * @param id
     * @return
     */
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo(id);

        log.info("***** result:" + result);
        return result;
    }

    /**
     * 使用Jmeter进行压测, 400个线程, 每个线程访问100次, 模拟4W个请求
     * 当前请求处理较慢, 需要3s, 在高并发情况下占用极多系统资源, 会导致其他请求也受到影响变慢
     * @param id
     * @return
     */
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfoTimeout(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfoTimeout(id);

        log.info("***** result:" + result);
        return result;
    }

    // ************************ 服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);

        log.info("***** result:" + result);
        return result;
    }
}
