package com.zzd.niodemo.nettyprotocol.privateprotocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 说明类的用途
 * @ClassName LoginAuthRespHandler
 * @Author zzd
 * @Create 2019/9/5 15:15
 * @Version 1.0
 **/
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    // 白名单，暂时简单处理，写死了
    private String[] writeList = {"/127.0.0.1"};
    // 已经登录的IP缓存，防止重复登录
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 如果是握手请求消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ) {
            System.out.println("receive client login req : " + message);
            NettyMessage loginResp = null;
            String nodeIndex = ctx.channel().remoteAddress().toString().split(":")[0];
            // 判断是否重复登录
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse("login_repeat");
            } else {
                // 判断是否属于白名单中的IP
                boolean tag = false;
                for (String ip : writeList) {
                    if (ip.equals(nodeIndex)) {
                        tag = true;
                        break;
                    }
                }
                if (tag) {
                    nodeCheck.put(nodeIndex, true);
                    loginResp = buildResponse("login_ok");
                } else {
                    loginResp = buildResponse("login_fail");
                }
                ctx.writeAndFlush(loginResp);
                System.out.println("send login resp is : " + loginResp);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(String result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP);
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            ctx.fireExceptionCaught(cause);
        } finally {
            // 异常发生时，把登录缓存清除
            nodeCheck.remove("/127.0.0.1");
        }
    }


}
