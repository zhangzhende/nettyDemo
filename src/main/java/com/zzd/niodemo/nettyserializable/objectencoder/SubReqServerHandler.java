package com.zzd.niodemo.nettyserializable.objectencoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description 说明类的用途
 * @ClassName TimeServerHandler
 * @Author zzd
 * @Create 2019/8/30 15:38
 * @Version 1.0
 **/
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubScribeReq body = (SubScribeReq) msg;
        System.out.println("the time server receive order :" + body.toString());
        ctx.writeAndFlush(getResp(body.getSubReqID()));
    }

    private SubScribeResq getResp(Integer subReqID) {
        SubScribeResq resq = new SubScribeResq();
        resq.setSunReqID(subReqID);
        resq.setRespCode(0);
        resq.setDesc("got it ,thank you ");
        return resq;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
