package com.zzd.niodemo.nettyprotocol.httpxml;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * @Description 说明类的用途
 * @ClassName HttpXmlResponseDecoder
 * @Author zzd
 * @Create 2019/9/3 19:59
 * @Version 1.0
 **/
public class HttpXmlResponseDecoder extends  AbstractHttpXmlDecoder {

    public HttpXmlResponseDecoder(Class clazz) {
        this(clazz, false);
    }
    public HttpXmlResponseDecoder(Class clazz, boolean isPrintlog) {
        super(clazz, isPrintlog);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Object o, List out) throws Exception {
        DefaultFullHttpResponse msg = (DefaultFullHttpResponse)o;
        HttpXmlResponse resHttpXmlResponse = new HttpXmlResponse(msg, decode0(
                ctx, msg.content()));
        out.add(resHttpXmlResponse);
    }
}
