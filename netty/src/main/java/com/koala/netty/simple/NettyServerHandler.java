package com.koala.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

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
 *      说明：
 *          1. 我们自定义一个Handler 需要继承netty 规定好的某个HandlerAdapter(规范)
 *          2. 这时我们自定义一个Handler , 才能称为一个handler
 *
 * Create by koala on 2022-08-27
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件(这里我们可以读取客户端发送的消息)：
     *   1. ChannelHandlerContext ctx：上下文对象, 含有 管道pipeline , 通道channel, 地址
     *   2. Object msg：就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName() + " channle =" + ctx.channel());//此处打断点，查看 ChannelHandlerContext/Channel/ChannelPipeline 中包含的信息
        System.out.println("server ctx =" + ctx);
        System.out.println("看看channel 和 pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链接, 出站入站

        //将 msg 转成一个 ByteBuf
        //ByteBuf 是 Netty 提供的，不是 NIO 的 ByteBuffer.
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + channel.remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是 write + flush
        //将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵", CharsetUtil.UTF_8));
    }

    //处理异常, 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
