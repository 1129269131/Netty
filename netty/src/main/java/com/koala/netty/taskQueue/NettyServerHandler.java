package com.koala.netty.taskQueue;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * day03：
 *      taskQueue自定义任务（解决耗时业务阻塞问题）
 *
 *      测试：
 *          0、分别解开：问题演示0、解决方案1、解决方案2 的块注释
 *          1、启动 com.koala.netty.taskQueue.NettyServer 服务端
 *          2、启动 com.koala.netty.taskQueue.NettyClient 客户端
 *          3、查看服务器端/客户端输出
 *          4、com.koala.netty.taskQueue.NettyServer中：非当前Reactor线程调用Channel的各种方法3 解开注释
 *          5、并启动多个NettyClient客户端，查看服务器端输出，验证不同客户端的hashcode是不一样的
 *
 * Create by koala on 2022-08-28
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据事
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //比如这里我们有一个非常耗时长的业务-> 异步执行 -> 提交该channel 对应的
        //NIOEventLoop 的 taskQueue中

        //问题演示0：阻塞5秒后，NettyClient端控制台才输出消息
        /*Thread.sleep(5 * 1000);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵2", CharsetUtil.UTF_8));*/

        //解决方案1：用户程序自定义的普通任务
        /*ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵2", CharsetUtil.UTF_8));
                    System.out.println("channel code=" + ctx.channel().hashCode());
                } catch (Exception ex) {
                    System.out.println("发生异常" + ex.getMessage());
                }
            }
        });

        //此处代码说明队列中可以添加多个异步任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵3", CharsetUtil.UTF_8));
                    System.out.println("channel code=" + ctx.channel().hashCode());
                } catch (Exception ex) {
                    System.out.println("发生异常" + ex.getMessage());
                }
            }
        });*/

        //解决方案2：用户自定义定时任务 --》该任务是提交到 scheduleTaskQueue 中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵4", CharsetUtil.UTF_8));
                    System.out.println("channel code=" + ctx.channel().hashCode());
                } catch (Exception ex) {
                    System.out.println("发生异常" + ex.getMessage());
                }
            }
        }, 5, TimeUnit.SECONDS);


        System.out.println("go on ...");//此处打断点，查看 ChannelHandlerContext --》pipeline --》channel --》eventLoop --》taskQueue/scheduleTaskQueue 的数量 ，验证异步任务是否已经加入到任务队列中
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵1", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
