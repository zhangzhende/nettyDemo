http协议的弊端：
1.http协议为半双工协议。就是在同一时刻，服务端和客户端只会有一端发送消息，不能同时发送消息。
2.http消息冗长繁琐。
3.易遭攻击

websocket协议特点：
单一的TCP连接，是全双工通信协议
对代理、防火墙、和路由器透明
无头部信息cookie和身份验证
无安全开销
通过ping/pong帧保持链路激活
服务器可以主动传递消息给客户端，不需要客户端轮询



