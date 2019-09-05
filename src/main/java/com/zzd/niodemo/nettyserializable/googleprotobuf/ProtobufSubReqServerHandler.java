package com.zzd.niodemo.nettyserializable.googleprotobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description 说明类的用途
 * @ClassName TimeServerHandler
 * @Author zzd
 * @Create 2019/8/30 15:38
 * @Version 1.0
 **/
public class ProtobufSubReqServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq body = (SubscribeReqProto.SubscribeReq) msg;
        System.out.println("the time server receive order :" + body.toString());
        ctx.writeAndFlush(getResp(body.getSubReqID()));
    }

    private SubscribeRespProto.SubscribeResp getResp(Integer subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("got it ,thank you ");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
