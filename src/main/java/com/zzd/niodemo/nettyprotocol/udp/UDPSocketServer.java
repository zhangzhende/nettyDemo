package com.zzd.niodemo.nettyprotocol.udp;

import com.zzd.niodemo.nettyprotocol.websocket.WebSocketServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description 使用netty开发nio server
 * <p>
 * 正确示范：使用UDP协议
 * 注意服务端是：NioServerSocketChannel
 * @ClassName SubReqServer
 * @Author zzd
 * @Create 2019/8/30 15:27
 * @Version 1.0
 **/
public class UDPSocketServer {
    public void bind(int port) {
//        配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
//                    因为UDP不存在客户端与服务端的实际连接，故不需要为连接（ChannelPipeline）设置handler
                    .handler(new UDPSocketServerHandler());
//        绑定端口，异步等待成功
            b.bind(port).sync().channel().closeFuture().sync();
            System.out.println("UDP socket server started at port :" + port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            优雅退出，释放线程资源
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        new UDPSocketServer().bind(port);
    }
}
