package com.koala.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

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

public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用");
        //在 ReplayingDecoder 不需要判断数据是否足够读取，内部会进行处理判断
        out.add(in.readLong());
    }
}
