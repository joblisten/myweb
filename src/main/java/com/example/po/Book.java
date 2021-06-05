package com.example.po;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Book implements Serializable {
    private Integer id;  //订单id
    private String uname;
    private String aname;
    private String pname;
    private Integer num;
    private Double total;
    private String number;
    private String address;
    private String other;
    private String sta;
    private String url;
    private String onumber;  //订单号,给用户
    private Date bdate;
    private String dealw; //受理


}
