package com.example.po;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Blog  implements Serializable {
    private Integer aid;
    private String aname;
    private String title;
    private String introduction;
    private String content;
    private Date adate;
    private String aimages;
    private String istop;
    private Integer pageview;

}
