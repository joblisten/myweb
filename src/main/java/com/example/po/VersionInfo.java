package com.example.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class VersionInfo implements Serializable {
    private Integer id;
    private Integer version;
}
