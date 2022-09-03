package com.koala.netty.codec2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * day09：
 *      ProtoBuf传输多种类型
 *
 *      测试：
 *          1、启动 com.koala.netty.codec2.NettyServer 服务端
 *          2、启动 com.koala.netty.codec2.NettyClient 客户端（启动多个进行测试）
 *          3、查看服务端控制台输出的信息
 *
 *      PS：
 *          1、使用 com.koala.netty.codec2/exe/protoc.exe 对 com.koala.netty.codec2.Student.proto 文件执行生成 MyDataInfo.java 对象
 *              1.1、进入 com.koala.netty.codec2/exe/protoc.exe 所在的文件夹
 *              1.2、cmd 打开
 *              1.3、执行命令：protoc.exe --java_out=. Student.proto
 *              1.4、生成的文件就可以在 com.koala.netty.codec2 项目中使用了
 *
 * Create by koala on 2022-09-02
 */

public class NettyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //在pipeline加入ProtoBufDecoder
                            //指定对哪种对象进行解码
                            pipeline.addLast("decoder", new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println(".....服务器 is ready...");

            ChannelFuture cf = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
