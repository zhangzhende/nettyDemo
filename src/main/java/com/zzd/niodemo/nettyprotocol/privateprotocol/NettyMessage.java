package com.zzd.niodemo.nettyprotocol.privateprotocol;

/**
 * @Description 说明类的用途
 * @ClassName NettyMessage
 * @Author zzd
 * @Create 2019/9/5 11:57
 * @Version 1.0
 **/
public class NettyMessage {

    private Header header;

    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
