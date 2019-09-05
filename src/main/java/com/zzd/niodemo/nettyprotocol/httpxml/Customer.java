package com.zzd.niodemo.nettyprotocol.httpxml;

import lombok.Data;

import java.util.List;

/**
 * @Description 说明类的用途
 * @ClassName Customer
 * @Author zzd
 * @Create 2019/9/3 19:22
 * @Version 1.0
 **/
@Data
public class Customer {
    private long customerNumber;
    /**
     * Personal name.
     */
    private String firstName;
    /**
     * Family name.
     */
    private String lastName;
    /**
     * Middle name(s), if any.
     */
    private List middleNames;
}
