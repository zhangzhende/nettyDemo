package com.zzd.niodemo.nettyprotocol.httpxml;

import lombok.Data;

/**
 * @Description 说明类的用途
 * @ClassName Order
 * @Author zzd
 * @Create 2019/9/3 19:22
 * @Version 1.0
 **/
@Data
public class Order {
    private long orderNumber;
    private Customer customer;
    private Address billTo;
    private Shipping shipping;
    private Address shipTo;
    private Float total;
}
