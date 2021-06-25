package com.example.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserBehavior implements Serializable {
    private Integer id;
    private Integer userid;
    private String ip;
    private String address;
    private String event;
    private String content;
    private Date date;

}
