package com.example.po;

import lombok.Data;

import java.util.Date;
@Data
public class Commentary {
    private Integer id;
    private String  name;
    //博客id
    private Integer bid;
    private Date    bdate;
    private String content;


}
