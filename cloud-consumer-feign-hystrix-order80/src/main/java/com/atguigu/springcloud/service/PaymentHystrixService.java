package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
// fallback 表示服务请求超时/出错时, PaymentFallbackService进行备选响应
@FeignClient(value = "CLOUD-PAYMENT-SERVICE", fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {

    /**
     * 服务绑定, 当前Service与目标Controller绑定
     * 与cloud-provider-hystrix-payment8001 服务中 PaymentController 绑定
     */
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfoTimeout(@PathVariable("id") Integer id);
}
