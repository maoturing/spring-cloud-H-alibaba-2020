package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * 生效前提是需要在父接口中设置备选响应  fallback = PaymentFallbackService.class
 *
 * @author mao  2021/2/28 15:23
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {

    /**
     * 使8001支付服务停机, 该访问也会出现问题, 此时使用该方法作为备选响应
     * 调用该方法的Controller不需要配置@HystrixCommand, 直接在service这里拦截
     * <p>
     * http://localhost/consumer/payment/hystrix/ok/2
     */
    @Override
    public String paymentInfo(Integer id) {
        return "--------访问 paymentInfo 出错, 交给PaymentFallbackService处理...o(╥﹏╥)o呜呜";
    }

    /**
     * 使8001支付服务停机, 该访问也会出现问题, 但是在Controller设置了@HystrixCommand备选响应, 所以该方法不会被执行
     */
    @Override
    public String paymentInfoTimeout(Integer id) {
        return "--------访问 paymentInfoTimeout 出错, 交给PaymentFallbackService处理...o(╥﹏╥)o呜呜";
    }
}
