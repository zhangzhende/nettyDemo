package com.zzd.niodemo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @Description 说明类的用途
 * @ClassName AsyncTimeServerHandler
 * @Author zzd
 * @Create 2019/8/28 14:10
 * @Version 1.0
 **/
public class AsyncTimeServerHandler implements Runnable {

    private int port;

    CountDownLatch latch;

    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
//            创建异步服务通道
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
//            监听端口
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("the time Server start in port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
//            操作完成之前一直阻塞
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept() {
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
