package com.zzd.niodemo.nettyprotocol.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description 说明类的用途
 * @ClassName HttpFileServer
 * @Author zzd
 * @Create 2019/9/3 11:32
 * @Version 1.0
 **/
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/main/java";

    public void run(final int port, final String url) {
//        配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChildChannelHandler())
            ;
//        绑定端口，异步等待成功
            ChannelFuture future = b.bind("192.168.30.29", port).sync();
            System.out.println("文件服务器启动。。。。。。");
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
            socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
//            作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse，因为http解码器在每个http消息中会生成多个消息对象
            socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
            socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
//            作用：支持异步发送大的码流，如大文件传输，但是不占用过多的内存，防止java内存溢出
            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
            socketChannel.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(DEFAULT_URL));
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        String url = DEFAULT_URL;
        new HttpFileServer().run(port, url);
    }
}
