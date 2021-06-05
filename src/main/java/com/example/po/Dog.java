package com.example.po;

import lombok.Data;

import java.io.Serializable;
@Data
public class Dog implements Serializable {
    static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String style;
    private String url;
    private String cname;
    private double price;
}