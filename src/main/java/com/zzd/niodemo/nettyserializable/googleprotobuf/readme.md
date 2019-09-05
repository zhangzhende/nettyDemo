使用Google Protobuf作为编解码框架

需要使用protoc去定义生成java文件，而且文件还最好不要改

windows安装：
https://github.com/protocolbuffers/protobuf/releases
网站下载windows64位，
解压，
然后配置环境变量path，将bin目录添加进去
然后即可运行protoc命令


优势：
1.跨语言。支持多种语言，C++，java，python等
2.编码后消息更小，有利于存储和传输
3.编解码性能更高
4.支持不同版本前向兼容
5.支持定义可选和必选字段

例子：
定义文件1内容：【SubscribeResp.proto】
syntax="proto2";
package netty;
option java_package="com.zzd.niodemo.nettyserializable.googleprotobuf";
option java_outer_classname="SubscribeRespProto";

message SubscribeResp{
  required int32 subReqID=1;
  required int32 respCode=2;
  required string desc=3;
}

定义文件2内容：【SubscribeReq.proto】
syntax="proto2";
package netty;
option java_package="com.zzd.niodemo.nettyserializable.googleprotobuf";
option java_outer_classname="SubscribeReqProto";

message SubscribeReq{
  required int32 subReqID=1;
  required string userName=2;
  required string productName=3;
  repeated string address=4;
}

注意：repeated: 指的是集合
然后执行命令; 
protoc --java_out=./output .\defineFile\SubscribeResp.proto
生成对应的java代码