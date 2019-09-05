package com.zzd.niodemo.nettyserializable.googleprotobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 说明类的用途
 * ChannelInboundHandlerAdapter:监听IN事件
 * ChannelOutboundHandlerAdapter：监听自己的IO操作
 * @ClassName TimeClientHandler
 * @Author zzd
 * @Create 2019/8/30 17:07
 * @Version 1.0
 **/
public class ProtobufSubReqClientHandler extends ChannelInboundHandlerAdapter {

    public ProtobufSubReqClientHandler() {
    }

    private SubscribeReqProto.SubscribeReq getSubReq(Integer i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setProductName("月饼");
        builder.setUserName("mr zhang");
        List<String> address=new ArrayList<>();
        address.add("南京市");
        address.add("beijingshi");
        builder.addAllAddress(address);
        return builder.build();
    }

    /**
     * 链接建立成功后调用本方法，发送查询时间的指令给服务端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(getSubReq(i));
        }
        ctx.flush();
    }

    /**
     * 当服务端返回信息的时候本方法被调用，
     *
     * @param ctx
     * @param msg 此时msg已经是解码成字符串以后的应答消息了
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("now is :" + msg );
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
