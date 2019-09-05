package com.zzd.niodemo.nettybag.rightwithFixedLength;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description 说明类的用途
 * @ClassName TimeServerHandler
 * @Author zzd
 * @Create 2019/8/30 15:38
 * @Version 1.0
 **/
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private int counter=0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("the time server receive order :" + body + ",the counter is " + (++counter));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
