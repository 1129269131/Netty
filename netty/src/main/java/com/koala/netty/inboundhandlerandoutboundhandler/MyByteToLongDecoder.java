package com.koala.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * day11：
 *      解码器-ReplayingDecoder
 *
 *      测试：
 *          1、启动 com.koala.netty.inboundhandlerandoutboundhandler.MyServer 服务端
 *          2、启动 com.koala.netty.inboundhandlerandoutboundhandler.MyClient 客户端
 *          3、 com.koala.netty.inboundhandlerandoutboundhandler.MyClientInitializer / com.koala.netty.inboundhandlerandoutboundhandler.MyServerInitializer
 *          4、关键处：// 注释/解开 进行测试
 *          5、查看服务端控制台输出的信息
 *
 * Create by koala on 2022-09-02
 */

public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *
     * decode 会根据接收到的数据，被调用多次, 直到确定没有新的元素被添加到list, 或者是ByteBuf 没有更多的可读字节为止
     * 如果list out 不为空，就会将list的内容传递给下一个 channelinboundhandler处理, 该处理器的方法也会被调用多次
     *
     * @param ctx 上下文对象
     * @param in 入站的 ByteBuf
     * @param out List 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        //因为 long 8个字节, 需要判断有8个字节，才能读取一个long
        if(in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
