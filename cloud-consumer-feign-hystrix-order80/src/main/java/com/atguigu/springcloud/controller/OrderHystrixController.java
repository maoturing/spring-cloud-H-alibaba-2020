package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mao  2021/2/25 2:09
 */

// 配置Hystrix全局备选响应, 反是没有配置fallbackMethod的都使用该方法处理
@DefaultProperties(defaultFallback = "paymentGlobalFallbackHandler")
@RestController
@Slf4j
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String getPaymentById(@PathVariable("id") Integer id) {
        log.info("直接调用本地接口paymentFeignService, 访问远程的payment服务: /payment/hystrix/ok/{id}");

        return paymentHystrixService.paymentInfo(id);
    }


    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public String paymentFeignTimeout(@PathVariable("id") Integer id) {
        log.info("直接调用本地接口paymentFeignService, 访问远程的payment服务: /payment/hystrix/timeout/{id}");
        String result = paymentHystrixService.paymentInfoTimeout(id);

        return result;
    }

    /**
     * 服务降级后的备选响应方法
     * http://localhost/consumer/payment/hystrix/timeout/2
     * @param id
     * @return
     */
    public String paymentInfoTimeoutHandler(Integer id) {
        return "我是消费者80, 对方支付系统繁忙, 请10s后重试...o(╥﹏╥)o呜呜";
    }


    /**
     * 用于测试Hystrix 全局备选响应
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/global/timeout/{id}")
    @HystrixCommand     // 不指定备选响应, 使用全局默认响应
    public String paymentFeignTimeout2(@PathVariable("id") Integer id) {
        // 访问超时付出, 调用全局备选响应paymentGlobalFallbackHandler()
        String result = paymentHystrixService.paymentInfoTimeout(id);

        return result;
    }
    /**
     * Hystrix全局备选响应
     * 该方法必须无参
     * http://localhost/consumer/payment/hystrix/global/timeout/2
     * @return
     */
    public String paymentGlobalFallbackHandler() {
        return "全局处理Fallback";
    }
}
