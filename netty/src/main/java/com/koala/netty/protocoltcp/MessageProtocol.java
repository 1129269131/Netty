package com.koala.netty.protocoltcp;

/**
 * day14：
 *      自定义协议解决TCP粘包拆包-协议包
 *
 * Create by koala on 2022-09-03
 */
public class MessageProtocol {
    private int len; //关键

    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
