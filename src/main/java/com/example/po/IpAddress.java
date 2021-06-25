package com.example.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpAddress implements Serializable {
    private Integer id;
    private String ip;
    private String address;

}
