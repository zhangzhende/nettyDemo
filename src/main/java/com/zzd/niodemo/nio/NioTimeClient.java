package com.zzd.niodemo.nio;

/**
 * @Description nioClient
 * 这里并没有考虑半包读写
 * @ClassName NioTimeClient
 * @Author zzd
 * @Create 2019/8/28 11:04
 * @Version 1.0
 **/
public class NioTimeClient {

    public static void main(String[] args) {
        int port =8080;
        new Thread(new NioTimeClientHandle("127.0.0.1",port),"TimeClient-001").start();
    }
}
