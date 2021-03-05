package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author mao  2021/2/25 1:17
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    // 远程调用传递过来的是application/json, 形如 {"aaa":"111","bbb":"222"}, 必须用@RequestBody接收, 否则payment为null
    // 传递过来的是application/x-www-form-urlencoded类型, 形如aaa=111&bbb=222, 不可以使用@RequestBody注解
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果: " + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功, 返回结果: "
                    + result + "\t serverPort: " + serverPort, payment);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果: " + payment);

        if (payment == null) {
            return new CommonResult<Payment>(444, "没有对应记录, 查询ID: " + id, null);
        } else {
            return new CommonResult<Payment>(200, "查询成功, serverPort:" + serverPort, payment);
        }
    }
    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    /**
     * 测试调用超时
     * @return
     */
    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }


}
