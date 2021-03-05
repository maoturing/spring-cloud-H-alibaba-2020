package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，
 * 这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类
 * 进行扫描，并用于构建bean定义，初始化Spring容器。
 * @author mao  2021/2/28 1:57
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
