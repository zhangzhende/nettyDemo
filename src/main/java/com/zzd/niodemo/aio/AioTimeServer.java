package com.zzd.niodemo.aio;

/**
 * @Description AIO 服务端
 * AIO =事件驱动I/O
 * @ClassName AioTimeServer
 * @Author zzd
 * @Create 2019/8/28 14:09
 * @Version 1.0
 **/
public class AioTimeServer {
    public static void main(String[] args) {
        int port=8081;
        AsyncTimeServerHandler timeServer=new AsyncTimeServerHandler(port);
        new Thread(timeServer,"AIO-timeserver-001").start();
    }
}
