LineBasedFrameDecoder的工作原理是依次便利ByteBuf中的刻度子节，
判断看是否有”\n” 或者“\r”，如果有，就以此为止为结束位置，
从可读索引到结束位置区间的字节久组成了一行。它是以换行符为结束标志的解码器，
支持携带结束符或者不携带结束符两种编码方式，同时支持配置单行的最大长度后仍
然没有发现换行符，就会抛出异常，同时忽略掉之前读到的异常码流.

StringDecoder就是讲收到的对象转换成字符串，然后继续调用后面的handler

LineBasedFrameDecoder+StringDecoder组合就是按行切换的文本解码器

注意：示例能通，就是channleRead方法一直无法被调用