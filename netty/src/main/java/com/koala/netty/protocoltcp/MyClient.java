package com.koala.netty.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * day14：
 *      自定义协议解决TCP粘包拆包
 *
 *      测试：
 *          1、启动 com.koala.netty.protocoltcp.MyServer 服务端
 *          2、启动 com.koala.netty.protocoltcp.MyClient 客户端（可启动多次查看）
 *          3、查看服务端控制台输出的信息
 *
 *      PS：
 *          核心类：com.koala.netty.protocoltcp.MessageProtocol
 *
 * Create by koala on 2022-09-03
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
