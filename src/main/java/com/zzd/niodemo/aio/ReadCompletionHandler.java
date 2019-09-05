package com.zzd.niodemo.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Description 说明类的用途
 * @ClassName ReadCompletionHandler
 * @Author zzd
 * @Create 2019/8/28 14:31
 * @Version 1.0
 **/
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if(this.channel==null){
        this.channel = channel;
        }
    }

    public void doWrite(String currentTime){
        if(currentTime!=null && currentTime.trim().length()>0){
            byte[] bytes=currentTime.getBytes();
            ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if(attachment.hasRemaining()){
                        channel.write(attachment,attachment,this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body=new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req=new String(body,"utf-8");
            System.out.println("the time server receive order :"+req);
            String currentTime="QUERY_TIME".equalsIgnoreCase(req)?System.currentTimeMillis()+"":"bad order";
            doWrite(currentTime);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
