package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author mao  2021/2/25 1:17
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    // 读取yml中当前服务的端口, 用于展示
    @Value("${server.port}")
    private String serverPort;

    // 服务发现, 对于注册进eureka里的微服务, 可以通过服务发现来获得该服务的信息
    // @EnableDiscoveryClient 主类使用开注解开启
    @Resource
    private DiscoveryClient discoveryClient;

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

    /**
     * 服务发现 DiscoveryClient, 利用该类可以查找Eureka注册中心的所有微服务
     * http://localhost:8001/payment/discovery
     */
    @GetMapping("/payment/discovery")
    public DiscoveryClient discovery() {
        // 查询Eureka下的所有服务
        List<String> services = discoveryClient.getServices();
        String str = services.stream().collect(Collectors.joining(","));
        log.info("Eureka下的所有微服务: " + str);

        // 查询某个服务下所有的实例
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        log.info("cloud-payment-service 微服务的所有实例: ");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + ":"
                    + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    /**
     * 测试自定义负载均衡算法, 返回被访问实例的端口, 用来验证轮询算法
     *
     * @return
     */
    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    /**
     * 测试调用超时
     *
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

    //=========== zipkin=============
    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "hi, i am paymentzipkin server fall back, welcome to atguigu, O(∩_∩)O哈哈~";
    }
}
