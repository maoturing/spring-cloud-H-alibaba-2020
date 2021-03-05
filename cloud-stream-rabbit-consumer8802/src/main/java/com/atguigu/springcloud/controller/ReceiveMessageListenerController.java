package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author mao  2021/3/3 18:04
 */
@EnableBinding(Sink.class)     // 表示消费者

@Component
@Slf4j
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String port;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        String msg = message.getPayload();
        log.info("******* Recevice msg: " + msg + "\t port: " + port);
    }
}
