package com.koala.netty.simple;

import io.netty.util.NettyRuntime;

/**
 * day02：
 *      Netty入门-获取当前系统的cpu核数
 *
 *      测试：
 *          1、启动 com.koala.netty.simple.Test
 *          2、查看控制台输出
 *
 * Create by koala on 2022-08-27
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors());
    }
}
