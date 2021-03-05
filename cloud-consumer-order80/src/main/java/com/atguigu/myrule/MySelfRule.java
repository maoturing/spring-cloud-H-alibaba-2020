package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mao  2021/2/27 1:02
 */
@Configuration
public class MySelfRule {
    /**
     * 创建Ribbon负载均衡规则
     * 需要注意, 该Bean不能写在com.atguigu.springcloud, 会被spring扫描到
     * 否则自定义的配置类会被所有 Ribbon 客户端共享, 打不到特殊定制的目的
     * 即修改RestTemplate的请求规则
     * @return
     */
    @Bean
    public IRule myRule () {
        return new RandomRule();        // Ribbon负载均衡为随机访问
    }
}
