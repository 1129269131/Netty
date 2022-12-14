package com.koala.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * day02：
 *      Netty入门-服务端
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

public class NettyServer {
    public static void main(String[] args) throws Exception {

        //创建BossGroup 和 WorkerGroup
        //说明
        //1. 创建两个线程组 bossGroup 和 workerGroup
        //2. bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup完成
        //3. 两个都是无限循环
        //4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
        //   默认是：cpu核数 * 2（MultithreadEventLoopGroup.java 类中可查看具体代码实现）
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);//此处改为0/不填/1，并打断点查看 EventLoopGroup/EventLoopGroup 对象中包含的信息
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    //.handler(null) // 该 handler对应 bossGroup , childHandler 对应 workerGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象(匿名对象)
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给我们的workerGroup 的 EventLoop 对应的管道设置处理器

            System.out.println("...服务器 is ready...");

            //绑定一个端口并且同步, 生成了一个 ChannelFuture 对象
            //启动服务器(并绑定端口)
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //Future-Listener机制：给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口 6668 成功");
                    } else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
