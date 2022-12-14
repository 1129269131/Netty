package com.koala.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * day09：
 *      ProtoBuf传输多种类型-服务端handler实现方式2
 *
 *      说明：
 *          1. 我们自定义一个Handler 需要继续netty 规定好的某个HandlerAdapter(规范)
 *          2. 这时我们自定义一个Handler , 才能称为一个handler
 *
 * Create by koala on 2022-09-02
 */
public class NettyServerHandler2 extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    /**
     * 读取数据实际(这里我们可以读取客户端发送的消息)
     * @param ctx：上下文对象, 含有 管道pipeline , 通道channel, 地址
     * @param msg：就是客户端发送的数据 默认Object
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        //读取从客户端发送的StudentPojo.Student
        System.out.println("客户端发送的数据 id=" + msg.getId() + " 名字=" + msg.getName());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是 write + flush
        //将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵1", CharsetUtil.UTF_8));
    }

    //处理异常, 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
