package com.zzd.niodemo.nettyserializable.googleprotobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @Description 注意客户端是：NioSocketChannel
 * 正确示范
 * NioSocketChannel
 * @ClassName NettyTimeClient
 * @Author zzd
 * @Create 2019/8/30 17:04
 * @Version 1.0
 **/
public class ProtobufSubReqClient {

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            socketChannel.pipeline().addLast(new ProtobufDecoder(
                                    SubscribeRespProto.SubscribeResp.getDefaultInstance()
                            ));
                            socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            socketChannel.pipeline().addLast(new ProtobufEncoder());
                            socketChannel.pipeline().addLast(new ProtobufSubReqClientHandler());
                        }
                    });
//        发起异步链接操作
            ChannelFuture f = b.connect(host, port).sync();
//            等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            优雅关闭
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        new ProtobufSubReqClient().connect(port, "127.0.0.1");
    }
}
