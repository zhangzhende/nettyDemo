package com.zzd.niodemo.nio;

/**
 * @Description NIO client server
 * 这里并没有考虑半包读写
 *
 *
 * @ClassName NioTimeServer
 * @Author zzd
 * @Create 2019/8/28 9:29
 * @Version 1.0
 **/
public class NioTimeServer {
    public static void main(String[] args) {
        int port=8080;
        MultiplexerTimeServer timeServer=new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-timeServer-001").start();
    }
}
