package com.zzd.niodemo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 利用线程池伪异步
 * @ClassName FakeTimeServer
 * @Author zzd
 * @Create 2019/8/27 16:15
 * @Version 1.0
 **/
public class FakeTimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("the time server is start in port :" + port);
            Socket socket = null;
            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 1000);
            while (true) {
                socket = server.accept();
                pool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                System.out.println("the time server close");
                server.close();
                server = null;
            }
        }
    }
}
