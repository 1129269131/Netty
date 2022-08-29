package com.koala.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

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

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当通道就绪就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server: (>^ω^<)喵", CharsetUtil.UTF_8));
    }

    //当通道有读取事件时，会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址： "+ ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
