package com.zzd.niodemo.nettyprotocol.privateprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.io.IOException;

/**
 * @Description 说明类的用途
 * @ClassName NettyMarshallingEncoder
 * @Author zzd
 * @Create 2019/9/5 14:59
 * @Version 1.0
 **/
public class NettyMarshallingEncoder extends MarshallingEncoder {

    public NettyMarshallingEncoder(MarshallerProvider provider) throws IOException {
        super(provider);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        try {
            super.encode(ctx, msg, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
