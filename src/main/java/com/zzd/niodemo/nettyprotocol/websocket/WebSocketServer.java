package com.zzd.niodemo.nettyprotocol.websocket;

import com.zzd.niodemo.nettyserializable.objectencoder.SubReqServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description 使用netty开发nio server
 * <p>
 * 正确示范：WebSocketServer
 * 注意服务端是：NioServerSocketChannel
 * @ClassName SubReqServer
 * @Author zzd
 * @Create 2019/8/30 15:27
 * @Version 1.0
 **/
public class WebSocketServer {
    public void bind(int port) {
//        配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 100)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChildChannelHandler())
            ;
//        绑定端口，异步等待成功
            Channel ch = b.bind(port).sync().channel();
            System.out.println("Web socket server started at port :" + port);
//            等待服务端监听端口关闭
            ch.closeFuture().sync();
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

            socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
//            作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse，因为http解码器在每个http消息中会生成多个消息对象
            socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
//            作用：支持异步发送大的码流，如大文件传输，但是不占用过多的内存，防止java内存溢出
            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
            socketChannel.pipeline().addLast(new WebSocketServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        new WebSocketServer().bind(port);
    }
}
