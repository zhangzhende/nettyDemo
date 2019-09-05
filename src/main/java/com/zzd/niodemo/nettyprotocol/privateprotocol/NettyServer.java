package com.zzd.niodemo.nettyprotocol.privateprotocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @Description 说明类的用途
 * @ClassName NettyServer
 * @Author zzd
 * @Create 2019/9/5 15:23
 * @Version 1.0
 **/
public class NettyServer {
    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }

    public void bind() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, work).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.TCP_NODELAY, true).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
                    ch.pipeline().addLast(new NettyMessageEncoder());
                    ch.pipeline().addLast(new ReadTimeoutHandler(50));
                    ch.pipeline().addLast(new LoginAuthRespHandler());
                    ch.pipeline().addLast(new HeartBeatRespHandler());
                }
            });
            ChannelFuture f = b.bind(18080).sync();
            System.out.println("Netty Server start ok! post is 18080");
            f.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

}
