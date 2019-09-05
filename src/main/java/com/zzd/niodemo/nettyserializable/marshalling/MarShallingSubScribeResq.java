package com.zzd.niodemo.nettyserializable.marshalling;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName SubScribeResq
 * @Author zzd
 * @Create 2019/9/2 13:57
 * @Version 1.0
 **/
public class MarShallingSubScribeResq implements Serializable {
    private static final long serialVersionUID = 2068486836498361532L;
    private Integer sunReqID;

    private Integer respCode;

    private String desc;

    @Override
    public String toString() {
        return "SubScribeResq{" +
                "sunReqID=" + sunReqID +
                ", respCode='" + respCode + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public Integer getSunReqID() {
        return sunReqID;
    }

    public void setSunReqID(Integer sunReqID) {
        this.sunReqID = sunReqID;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
