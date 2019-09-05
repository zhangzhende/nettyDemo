package com.zzd.niodemo.nettyserializable.objectencoder;

import com.zzd.niodemo.nettybag.rightwithlinebased.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description 使用netty开发nio server
 *
 * 正确示范：使用ObjectDecoder代替java默认的序列化工具
 * 注意服务端是：NioServerSocketChannel
 * @ClassName SubReqServer
 * @Author zzd
 * @Create 2019/8/30 15:27
 * @Version 1.0
 **/
public class SubReqServer {
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
//            weakCachingConcurrentResolver使用线程安全的weakReferenceMap，当虚拟机内存不足时会释放缓存中的内存，防止内存泄露
            socketChannel.pipeline().addLast(new ObjectDecoder(
                    1024*1024,
                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
            socketChannel.pipeline().addLast(new ObjectEncoder());
            socketChannel.pipeline().addLast(new SubReqServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        new SubReqServer().bind(port);
    }
}
