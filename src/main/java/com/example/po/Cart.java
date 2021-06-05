package com.example.po;

import lombok.Data;

@Data
public class Cart {
    static final long serialVersionUID = 1L;
    private Integer id; //购物车id
    private Integer pid; //宠物id
    private String pname; //宠物名字
    private String uname; //购买名字
    private String url;
    private Integer num;
    private double price;
    private double total;

}
