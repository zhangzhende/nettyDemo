使用各种序列器：
java自身序列化的缺点：
1.无法跨语言。java的序列化是其内部额私有协议，其他语言并不支持。
2.序列化后的码流太大。
3.序列化性能太低。

由于java自身序列器序列化效果问题大，所以一般会换用其他序列化