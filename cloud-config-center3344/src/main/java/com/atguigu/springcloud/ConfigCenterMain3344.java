package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 访问下面链接读取配置中心的配置
 * http://config3344.com:3344/master/config-dev.yml
 * http://config3344.com:3344/dev/config-dev.yml
 *
 * @author mao  2021/3/2 17:36
 */
// 开启配置中心
@EnableConfigServer
@SpringBootApplication
public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}
