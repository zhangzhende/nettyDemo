package com.zzd.niodemo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 同步IO
 * @ClassName TimeServer
 * @Author zzd
 * @Create 2019/8/27 14:19
 * @Version 1.0
 **/
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("the time server is start in port: " + port);
            Socket socket = null;
            while (true) {
                socket=server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server!=null){
                System.out.println("The time server close");
                server.close();
                server=null;
            }
        }

    }


}
