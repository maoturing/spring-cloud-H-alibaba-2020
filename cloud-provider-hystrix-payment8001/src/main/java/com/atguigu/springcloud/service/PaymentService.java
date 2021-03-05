package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author mao  2021/2/28 2:57
 */
@Service
public class PaymentService {

    public String paymentInfo(Integer id) {
        return "线程池: " + Thread.currentThread().getName()
                + " paymentInfo_OK, id: " + id + "\t ^_^哈哈";
    }

    /**
     * 模拟超时, 测试Hystrix服务降级
     *
     * @HystrixCommand 设置服务降级的备选响应方法, 使用前要开启熔断器@EnableCircuitBreaker
     * @HystrixProperty timeoutInMilliseconds 设置服务超时时间, 超时后则使用fallbackMethod进行响应, 称为服务降级
     */
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfoTimeout(Integer id) {
        int time = 5;

//        int a = 1 / 0;      // 模拟异常

        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池: " + Thread.currentThread().getName()
                + " paymentInfo_Timeout, id: " + id + ",\t 耗时" + time + "秒, O(∩_∩)O哈哈~";

    }

    /**
     * 服务降级后的备选响应方法
     *
     * @param id
     * @return
     */
    public String paymentInfoTimeoutHandler(Integer id) {
        return "线程池: " + Thread.currentThread().getName()
                + " paymentInfo_Timeout_Handler, id: " + id + ",\t (Fallback) 系统繁忙或出错, 请稍后再试 o(╥﹏╥)o呜呜";
    }

    //***************服务熔断*********************

    /**
     * 下面配置的意思是  在10000ms内请求10次, 如果失败率达到60%, 则跳闸熔断, 调用fallbackMethod
     * 当然如果发生异常(id<0), 也会直接调用fallbackMethod
     *
     * 1. 多次使用(id<0)访问该方法(/payment/circuit/{id}), 重复刷新浏览器, 发生异常使得失败率达到60%, 触发熔断, 由fallbackMethod处理响应
     * 2. 使用(id>0)访问, 也会由fallbackMethod处理响应
     * 3. 等过一段时间, 熔断器会慢慢恢复, 使用(id>0)访问, 能够正常响应显示调用成功
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),      // 开启熔断器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),   // 时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")    // 失败率到达多少后跳闸熔断
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("****** id 不能为负数");
        }

        return "线程池: " + Thread.currentThread().getName() + "\t 调用成功O(∩_∩)O哈哈~ " + IdUtil.simpleUUID();
    }

    /**
     * 熔断后的响应
     *
     * http://localhost:8001/payment/circuit/-3   发生异常会使用该方法进行响应
     */
    public String paymentCircuitBreakerFallback(Integer id) {
        return "id = " + id + " 不能为负数, 请稍后再试 o(╥﹏╥)o";
    }

}