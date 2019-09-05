package com.zzd.niodemo.nettyprotocol.privateprotocol;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 说明类的用途
 * @ClassName Header
 * @Author zzd
 * @Create 2019/9/5 11:46
 * @Version 1.0
 **/
public final class Header {

    private int crcCode = 0xabef0101;
    //笑死长度
    private int length;
    //会话ID
    private long sessionID;
    //    消息类型
    private byte type;
    //消息优先级
    private byte priority;
    //    附件
    private Map<String, Object> attachment = new HashMap<>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
}
