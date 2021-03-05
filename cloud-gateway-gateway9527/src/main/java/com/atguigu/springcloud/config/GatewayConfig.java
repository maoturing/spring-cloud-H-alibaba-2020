package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 与yml中  cloud.gateway.routes的作用一样, 都是配置 Gateway
 * @author mao  2021/3/2 11:14
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 访问localhost:9527/game 转发到 news.baidu.com/game
        routes.route("payment_route_atguigu", r -> r.path("/game").uri("http://news.baidu.com")).build();
        return routes.build();
    }
    @Bean
    public RouteLocator getRouteLocator2(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 访问localhost:9527/guonei 转发到 news.baidu.com/guonei
        routes.route("payment_route_atguigu2", r -> r.path("/guonei").uri("http://news.baidu.com")).build();
        return routes.build();
    }
}
