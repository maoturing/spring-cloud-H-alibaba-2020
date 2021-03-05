package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @author mao  2021/3/5 1:09
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testa")
    public String testA() {
        return "----- testa";
    }

    @GetMapping("/testb")
    public String testB() {
        return "----- testb";
    }

    @GetMapping("/testc")
    public String testC() {
        log.info(Thread.currentThread().getName() + "\t" + "..testc");
        return "----- testc";
    }

    /**
     * 测试Sentinel服务降级
     *
     * 当每秒钟进来超过5个请求(默认)，我们希望RT=200ms处理完本次任务,
     * 如果超过200ms没有处理完, 那么在未来的5s的时间抽口内, 断路微服务 /testd 不可用
     *
     * 后续停止Jmeter, 时间窗口5s过后微服务 /testd 可以访问
     *
     */
    @GetMapping("/testd")
    public String testD() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1000);
        log.info(Thread.currentThread().getName() + "\t" + "..testd");
        return "----- testd";
    }

    @GetMapping("/teste")
    public String testE() throws InterruptedException {
        log.info("teste 异常比例");
        int age = 10 / 0;       // 异常比例100%
        return "----- testd";
    }
}
