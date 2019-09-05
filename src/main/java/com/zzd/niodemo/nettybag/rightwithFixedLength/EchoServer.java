package com.zzd.niodemo.nettybag.rightwithFixedLength;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description 使用netty开发nio server
 * <p>
 * 正确示范：使用FixedLengthFrameDecoder解决粘包问题
 * 注意服务端是：NioServerSocketChannel
 * 客户端不写了，懒
 * @ClassName EchoServer
 * @Author zzd
 * @Create 2019/8/30 15:27
 * @Version 1.0
 **/
public class EchoServer {
    public void bind(int port) {
//        配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChildChannelHandler())
            ;
//        绑定端口，异步等待成功
            ChannelFuture future = b.bind(port).sync();
//            等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            优雅退出，释放线程资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {

            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(20));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new EchoServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        new EchoServer().bind(port);
    }
}
