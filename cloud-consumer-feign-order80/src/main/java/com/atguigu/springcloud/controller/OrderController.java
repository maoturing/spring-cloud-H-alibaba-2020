package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mao  2021/2/25 2:09
 */
@RestController
@Slf4j
public class OrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        log.info("直接调用本地接口paymentFeignService, 访问远程的payment服务: /payment/get/{id}");

        return paymentFeignService.getPaymentById(id);
    }

    /**
     * Feign远程调用, 如果服务端处理超过1秒钟, 则Feign客户端直接报错
     * 在yml配置了超时时间后, 可以正常调用
     * @return
     */
    @GetMapping("/consumer/payment/feign/timeout")
    public String paymentFeignTimeout() {
        log.info("直接调用本地接口paymentFeignService, 访问远程的payment服务: /payment/feign/timeout");

        return paymentFeignService.paymentFeignTimeout();
    }
}
