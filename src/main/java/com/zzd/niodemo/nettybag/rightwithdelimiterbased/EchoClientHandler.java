package com.zzd.niodemo.nettybag.rightwithdelimiterbased;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description 说明类的用途
 * ChannelInboundHandlerAdapter:监听IN事件
 * ChannelOutboundHandlerAdapter：监听自己的IO操作
 * @ClassName TimeClientHandler
 * @Author zzd
 * @Create 2019/8/30 17:07
 * @Version 1.0
 **/
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    static final String ECHO_REQ = "hi welcome to netty. $_";
    private int counter;

    public EchoClientHandler() {
    }


    /**
     * 链接建立成功后调用本方法，发送查询时间的指令给服务端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    /**
     * 当服务端返回信息的时候本方法被调用，
     *
     * @param ctx
     * @param msg 此时msg已经是解码成字符串以后的应答消息了
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String body = (String) msg;
        System.out.println("now is :" + body + ",the counter is " + (++counter));

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
