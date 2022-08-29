package com.koala.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * day05：
 *      Unpooled应用实例1
 *
 *      测试：
 *          0、com.koala.netty.buf.NettyByteBuf01 中的 ByteBuf buffer = Unpooled.buffer(10); 处，打上断点
 *          1、启动 com.koala.netty.buf.NettyByteBuf01
 *          2、不断 Step Over，观察 ByteBuf 对象中的信息，及信息变化
 *
 * Create by koala on 2022-08-28
 */

public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个ByteBuf
        //说明
        //1. 创建 对象，该对象包含一个数组arr , 是一个byte[10]
        //2. 在netty 的buffer中，不需要使用flip 进行反转
        //   底层维护了 readerIndex 和 writerIndex
        //3. 通过 readerIndex 和  writerIndex 和  capacity， 将buffer分成三个区域
        // 0---readerIndex：已经读取的区域
        // readerIndex---writerIndex：可读的区域
        // writerIndex----capacity：可写的区域
        ByteBuf buffer = Unpooled.buffer(10);//此处打断点，运行NettyByteBuf01类，观察buffer中的信息，及读取index的信息变化

        for(int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity=" + buffer.capacity());//10

        //输出
        for(int i = 0; i<buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }

        System.out.println("----------------------");

        for(int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }

        System.out.println("执行完毕");
    }
}
