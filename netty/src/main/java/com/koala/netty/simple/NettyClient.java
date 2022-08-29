package com.koala.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * day02：
 *      Netty入门-客户端
 *
 *      测试：
 *          1、启动 com.koala.netty.simple.NettyServer 服务端
 *          2、启动 com.koala.netty.simple.NettyClient 客户端（客户端可启动多个进行测试）
 *          3、查看服务器端/客户端输出
 *          4、com.koala.netty.simple.NettyServer 中的 EventLoopGroup bossGroup = new NioEventLoopGroup(1); 处打断点调试
 *          5、com.koala.netty.simple.NettyClient 中的 System.out.println("服务器读取线程 " + Thread.currentThread().getName() + " channle =" + ctx.channel()); 处打断点调试
 *          6、debugger运行服务端/客户端客户端可启动多个进行测试）程序
 *          7、查看断点处的信息
 *
 * Create by koala on 2022-08-27
 */

public class NettyClient {
    public static void main(String[] args) throws Exception {

        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group) //设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现类(反射)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler()); //加入自己的处理器
                        }
                    });

            System.out.println("客户端 ok..");

            //启动客户端去连接服务器端
            //关于 ChannelFuture 要分析，涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
