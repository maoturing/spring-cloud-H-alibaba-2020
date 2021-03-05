package com.atguigu.springcloud.utils;

import java.time.ZonedDateTime;

/**
 * 在配置Gateway路由时, After断言规则需要用到时区时间, 获取时区时间的方法如下
 *
 * @author mao  2021/3/2 12:22
 */
public class TimeFormat {
    public static void main(String[] args) {
        // 获取当前时区时间, zoned 时区
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
    }
}
