package com.zzd.niodemo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description 说明类的用途
 * @ClassName NettyTimeClient
 * @Author zzd
 * @Create 2019/8/30 17:04
 * @Version 1.0
 **/
public class NettyTimeClient {

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
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
        new NettyTimeClient().connect(port, "127.0.0.1");
    }
}
