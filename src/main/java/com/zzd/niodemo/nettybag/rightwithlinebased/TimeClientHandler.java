package com.zzd.niodemo.nettybag.rightwithlinebased;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * @Description 说明类的用途
 * ChannelInboundHandlerAdapter:监听IN事件
 * ChannelOutboundHandlerAdapter：监听自己的IO操作
 * @ClassName TimeClientHandler
 * @Author zzd
 * @Create 2019/8/30 17:07
 * @Version 1.0
 **/
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] req;
    private int counter;

    public TimeClientHandler() {
        /*LineBasedFrameDecoder的工作原理是依次便利ByteBuf中的刻度子节，
        判断看是否有”\n” 或者“\r”，如果有，就以此为止为结束位置，
        从可读索引到结束位置区间的字节久组成了一行。它是以换行符为结束标志的解码器，
        支持携带结束符或者不携带结束符两种编码方式，同时支持配置单行的最大长度后仍
        然没有发现换行符，就会抛出异常，同时忽略掉之前读到的异常码流*/
//        理论上讲这里应该好使的，但是channelRead方法一直无法被调用，很烦
        req = ("QUERY_TIME" + System.getProperty("line.separator")).getBytes();
    }


    /**
     * 链接建立成功后调用本方法，发送查询时间的指令给服务端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
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

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("now is :" + "" + ",the counter is " + (++counter));
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
