package com.koala.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * day04：
 *      Http服务程序实例
 *
 *      测试：
 *          1、启动 com.koala.netty.http.TestServer 服务端
 *          2、浏览器访问：http://localhost:6668
 *          3、查看服务端/浏览器输出消息
 *          4、浏览器端F12查看请求、响应信息
 *
 * Create by koala on 2022-08-28
 */

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty 提供的httpServerCodec codec =>[coder - decoder]
        //HttpServerCodec 说明
        //1. HttpServerCodec 是netty 提供的处理http的 编-解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());

        //2. 增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

        System.out.println("ok~~~~");//此处打断点，浏览器访问：http://localhost:6668，研究ChannelPipeline，关键词：head、tail、handler、next、prev
    }
}
