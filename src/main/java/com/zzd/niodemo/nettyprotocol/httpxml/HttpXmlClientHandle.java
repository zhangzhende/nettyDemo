package com.zzd.niodemo.nettyprotocol.httpxml;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description 说明类的用途
 * @ClassName HttpXmlClientHandle
 * @Author zzd
 * @Create 2019/9/3 20:05
 * @Version 1.0
 **/
public class HttpXmlClientHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
        System.out.println("#########");
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        HttpXmlResponse msg = (HttpXmlResponse) o;
        System.out.println("The client receive response of http header is : " + msg.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : " + msg.getResult());
    }

//    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        HttpXmlResponse msg = (HttpXmlResponse) o;
        System.out.println("The client receive response of http header is : " + msg.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : " + msg.getResult());
    }
}
