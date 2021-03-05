package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
// 使用前提是开启 @EnableFeignClients
// 指定需要绑定的服务 CLOUD-PAYMENT-SERVICE
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    /**
     * 服务绑定, 将当前接口与 cloud-provider-payment8001 中  OrderController.getPaymentById() 绑定
     * 使得当前order消费者可以不发送RestTemplate请求, 直接调用 PaymentService 服务
     * @param id
     * @return
     */
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    /**
     * 绑定服务
     * @return
     */
    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout();

}
