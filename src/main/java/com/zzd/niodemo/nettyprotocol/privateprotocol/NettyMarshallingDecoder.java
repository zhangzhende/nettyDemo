package com.zzd.niodemo.nettyprotocol.privateprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * @Description 说明类的用途
 * @ClassName NettyMarshallingDecoder
 * @Author zzd
 * @Create 2019/9/5 15:03
 * @Version 1.0
 **/
public class NettyMarshallingDecoder extends MarshallingDecoder {
    public NettyMarshallingDecoder(UnmarshallerProvider provider, int objectMaxSize) {
        super(provider, objectMaxSize);
    }

    public NettyMarshallingDecoder(UnmarshallerProvider provider) {
        super(provider);
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        try {
            return super.decode(ctx, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
