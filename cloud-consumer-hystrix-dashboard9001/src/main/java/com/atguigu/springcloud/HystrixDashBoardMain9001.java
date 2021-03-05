package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author mao  2021/3/1 2:34
 */
@SpringBootApplication
// 开启图形化监控, 本质是actuator的包装. 被监控的服务必须在pom中引入了 actuator
// http://localhost:9001/hystrix
@EnableHystrixDashboard
public class HystrixDashBoardMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashBoardMain9001.class, args);
    }
}
