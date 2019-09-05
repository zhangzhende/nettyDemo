使用http+xml协议栈
模拟用户订购系统，通过http客户端向服务端发送订购请求，请求消息放在http消息体中，以xml承载
协议采用http1.1,连接类型为CLOSE方式【即双方交互完成，由http服务端主动关闭链路，随后客户端也关闭】

xml绑定框架JiBx


注意：有同样的问题，服务端无法接受客户端的消息，就是channelRead方法无法被调用，然后在客户端断开的时候触发一次channelReadComplete
//TODO 不知道为啥
