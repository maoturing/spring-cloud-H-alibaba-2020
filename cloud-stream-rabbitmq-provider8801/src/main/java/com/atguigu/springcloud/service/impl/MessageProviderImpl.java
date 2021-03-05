package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;
/**
 * //@EnableBinding 指信道channel和exchange绑定在一起
 * //@EnableBinding(Source.class) 就是将 Source(源) 放到 Channel 的意思
 *
 * 1、LISTENING状态
 * FTP服务启动后首先处于侦听（LISTENING）状态。
 *
 * 2、ESTABLISHED状态
 * ESTABLISHED的意思是建立连接。表示两台机器正在通信。
 * @author mao  2021/3/3 14:37
 */

//@EnableBinding 指信道channel和exchange绑定在一起
//@EnableBinding(Source.class) 就是将 Source(源) 放到 Channel 的意思
@EnableBinding(Source.class)     // 表示生产者
@Slf4j
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;      // 消息发送管道, 由stream-rabbit注入, 名称在yml中配置

    /**
     * 将消息发送到Exchange, Exchange在yml中配置
     * @return
     */
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        // 一定要注意MessageBuilder不要导错包
        output.send(MessageBuilder.withPayload(serial).build());

        log.info("***************serial: " + serial);
        System.out.println("发送消息: "+serial);
        return null;
    }
}
