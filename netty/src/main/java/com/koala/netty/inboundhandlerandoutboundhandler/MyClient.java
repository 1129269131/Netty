package com.koala.netty.inboundhandlerandoutboundhandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * day11：
 *      Handler链调用机制实例
 *
 *      测试：
 *          1、启动 com.koala.netty.inboundhandlerandoutboundhandler.MyServer 服务端
 *          2、启动 com.koala.netty.inboundhandlerandoutboundhandler.MyClient 客户端
 *          3、查看服务端控制台输出的信息
 *
 * Create by koala on 2022-09-02
 */

public class MyClient {
    public static void main(String[] args)  throws  Exception{

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer()); //自定义一个初始化类

            ChannelFuture channelFuture = bootstrap.connect("localhost", 7000).sync();

            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
