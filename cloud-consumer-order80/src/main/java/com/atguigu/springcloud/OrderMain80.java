package com.atguigu.springcloud;

import com.atguigu.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author mao  2021/2/25 2:07
 */
@EnableEurekaClient
// 使得MySelfRule负载规则生效, 修改 Ribbon 的负载均衡访问规则, name属性必须与 RestTemplate 请求的url大小写保持一致, 否则仍会采用轮询规则
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
//@RibbonClient(name = "cloud-payment-service", configuration = MySelfRule.class)
@SpringBootApplication
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
