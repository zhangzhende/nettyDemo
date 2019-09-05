package com.zzd.niodemo.nettyserializable.marshalling;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName SubScribeReq
 * @Author zzd
 * @Create 2019/9/2 13:54
 * @Version 1.0
 **/
public class MarShallingSubScribeReq implements Serializable {
    private static final long serialVersionUID = 3063028669005478694L;
    private Integer subReqID;

    private String userName;

    private String productName;

    private String phoneNumber;

    private String address;

    @Override
    public String toString() {
        return "SubScribeReq{" +
                "subReqID=" + subReqID +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(Integer subReqID) {
        this.subReqID = subReqID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
