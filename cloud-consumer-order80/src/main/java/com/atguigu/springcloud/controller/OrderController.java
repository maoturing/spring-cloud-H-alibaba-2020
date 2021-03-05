package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author mao  2021/2/25 2:09
 */
@RestController
@Slf4j
public class OrderController {
//    public static final String PAYMENT_URL = "http://localhost:8001";

    // RestTemplate bean 必须使用@LoadBalanced, 才能根据服务名在注册中心找到服务, 并轮询调用, 否则会报UnknownHostException
    // 自动去Eureka注册中心去查找PAYMENT服务, 轮询调用PAYMENT集群中的节点8001, 8002
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    /**
     * restTemplate.getForEntity() 返回ReponseEntity对象
     * restTemplate.getForObject() 返回json
     *
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            log.info("Code: " + entity.getStatusCode() + ", Body: " + entity.getBody());
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "查询失败");
        }
    }

    /**
     * 测试自定义负载均衡算法
     * @return
     */
    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB() {

        // 1. 获取所有服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        // 2. 使用自定义负载规则获取请求的目标实例, loadBalancer使用的是MyLB, 已经注入了spring容器
        ServiceInstance instance = loadBalancer.instance(instances);
        URI uri = instance.getUri();
        log.info("请求payment服务的目标实例: " + uri);

        return restTemplate.getForObject(uri + "/payment/lb", String.class);        // 这里的/payment/lb 也可以替换成其他链接, 只要能返回目标实例的端口就可以
    }

    // ==================zipkin + sleuth =====
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject(PAYMENT_URL+ "/payment/zipkin/", String.class);

        return result;
    }

}
