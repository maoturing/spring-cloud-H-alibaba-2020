package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义负载均衡算法, 将该类注入spring容器
 * @author mao  2021/2/27 2:06
 */
@Component
public class MyLB implements LoadBalancer {

    // 请求访问次数
    private AtomicInteger count = new AtomicInteger(0);

    @Resource
    private DiscoveryClient discoveryClient;

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        int size = instances.size();
        int index = count.getAndIncrement() % size;

        return instances.get(index);
    }
}
