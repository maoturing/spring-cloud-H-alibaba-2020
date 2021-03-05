package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author mao  2021/2/27 2:05
 */
public interface LoadBalancer {
    ServiceInstance instance(List<ServiceInstance> serviceInstances);
}
