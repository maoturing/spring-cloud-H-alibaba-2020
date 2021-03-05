package com.atguigu.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 自定义全局Gateway过滤器, 会影响所有请求
 *
 * @author mao  2021/3/2 16:18
 */
// @Component   // 测试完就注释掉, 因为会影响之前的访问
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {

    /**
     * 全局过滤器, 会影响全局访问, 所有访问必须携带参数 uname, 否则会报406
     * http://localhost:9527/payment/lb?uname=aaa
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***************** come in MyLogGatewayFilter *******");
        ServerHttpRequest request = exchange.getRequest();
        String uname = request.getQueryParams().getFirst("uname");

        if (uname == null) {
            log.info("***********用户名为null, 非法用户o(╥﹏╥)o***********");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        log.info("***********用户名为" + uname + ", 合法用户O(∩_∩) ***********");
        return chain.filter(exchange);
    }

    // 过滤器生效的顺序
    @Override
    public int getOrder() {
        return 0;
    }

}
