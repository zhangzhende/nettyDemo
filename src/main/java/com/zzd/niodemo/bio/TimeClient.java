package com.zzd.niodemo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Description 说明类的用途
 * @ClassName TimeClient
 * @Author zzd
 * @Create 2019/8/27 15:09
 * @Version 1.0
 **/
public class TimeClient {
    public static void main(String[] args) {
        int port =8080;
        Socket socket=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            socket=new Socket("127.0.0.1",port);

            in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY_TIME");
            System.out.println("Send order 2 server succeed");
            String resp=in.readLine();
            System.out.println("now is :"+resp);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
                out=null;
            }
            if(in!=null){
                try{
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in=null;
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket=null;
            }
        }

    }
}
