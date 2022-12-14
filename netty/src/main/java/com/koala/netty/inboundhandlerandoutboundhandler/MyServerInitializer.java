package com.koala.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//断点查看

        //入站的handler进行解码 MyByteToLongDecoder
        //pipeline.addLast(new MyByteToLongDecoder());// 注释/解开 进行测试
        pipeline.addLast(new MyByteToLongDecoder2());// 注释/解开 进行测试

        //出站的handler进行编码
        pipeline.addLast(new MyLongToByteEncoder());

        //自定义的handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
