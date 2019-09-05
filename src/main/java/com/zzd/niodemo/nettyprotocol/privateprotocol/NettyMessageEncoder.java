package com.zzd.niodemo.nettyprotocol.privateprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description 说明类的用途
 * @ClassName NettyMessageEncoder
 * @Author zzd
 * @Create 2019/9/5 15:04
 * @Version 1.0
 **/
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    NettyMarshallingEncoder nettyMarshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        nettyMarshallingEncoder = MarShallingCodeCFactory.buildMarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> list) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionID());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());
        sendBuf.writeInt(msg.getHeader().getAttachment().size());
        for (Map.Entry<String, Object> entry : msg.getHeader().getAttachment().entrySet()) {
            String key = entry.getKey();
            byte[] keyArray = key.getBytes("UTF-8");
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            Object value = entry.getValue();
            nettyMarshallingEncoder.encode(ctx, value, sendBuf);
        }
        if (msg.getBody() != null) {
            nettyMarshallingEncoder.encode(ctx, msg.getBody(), sendBuf);
        }
        sendBuf.setInt(4, sendBuf.readableBytes());
        list.add(sendBuf);
    }
}
