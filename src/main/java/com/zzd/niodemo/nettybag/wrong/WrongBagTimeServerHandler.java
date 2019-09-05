package com.zzd.niodemo.nettybag.wrong;

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
public class WrongBagTimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
//        理论上服务端接收的消息总数跟客户端相同，并且请求消息删除回车换行符以后应该为QUERY_TIME
        String body = new String(req, "utf-8").substring(0, req.length - System.getProperty("line.separator").length());
        System.out.println("the time server receive order :" + body + ",the counter is " + (++counter));
        String currentTime = "QUERY_TIME".equalsIgnoreCase(body) ? System.currentTimeMillis() + "" : "bad order";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

}
