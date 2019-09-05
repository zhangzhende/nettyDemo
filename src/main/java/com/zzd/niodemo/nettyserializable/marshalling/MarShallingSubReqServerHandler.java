package com.zzd.niodemo.nettyserializable.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description 说明类的用途
 * @ClassName TimeServerHandler
 * @Author zzd
 * @Create 2019/8/30 15:38
 * @Version 1.0
 **/
public class MarShallingSubReqServerHandler extends ChannelInboundHandlerAdapter {

    //    理论上讲这个应该会被调用，但是一直无法被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        MarShallingSubScribeReq body = (MarShallingSubScribeReq) msg;
        System.out.println("the time server receive order :" + body.toString());
        ctx.writeAndFlush(getResp(body.getSubReqID()));
    }

    private MarShallingSubScribeResq getResp(Integer subReqID) {
        MarShallingSubScribeResq resq = new MarShallingSubScribeResq();
        resq.setSunReqID(subReqID);
        resq.setRespCode(0);
        resq.setDesc("got it ,thank you ");
        return resq;
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx)  {
//        System.out.println("the time server receive order channelReadComplete" );
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        ctx.close();
    }

}
