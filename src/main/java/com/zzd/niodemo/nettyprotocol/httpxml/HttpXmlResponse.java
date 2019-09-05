package com.zzd.niodemo.nettyprotocol.httpxml;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @Description 说明类的用途
 * @ClassName HttpXmlResponse
 * @Author zzd
 * @Create 2019/9/3 19:58
 * @Version 1.0
 **/
public class HttpXmlResponse {

    private FullHttpResponse httpResponse;
    private Object result;
    public HttpXmlResponse(FullHttpResponse httpResponse, Object result) {
        this.httpResponse = httpResponse;
        this.result = result;
    }
    public final FullHttpResponse getHttpResponse() {
        return httpResponse;
    }
    public final void setHttpResponse(FullHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
    public final Object getResult() {
        return result;
    }
    public final void setResult(Object result) {
        this.result = result;
    }
}
