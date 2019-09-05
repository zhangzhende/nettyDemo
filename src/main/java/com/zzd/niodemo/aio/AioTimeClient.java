package com.zzd.niodemo.aio;

/**
 * @Description 说明类的用途
 * @ClassName AioTimeClient
 * @Author zzd
 * @Create 2019/8/30 11:53
 * @Version 1.0
 **/
public class AioTimeClient {
    public static void main(String[] args) {
        int port=8081;
        new Thread(new AsyncTimeClientHandler("127.0.0.1",port),"AIO-client-001").start();
    }
}
