package com.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户返回体
 */
@Data
public class ResultVo<T> implements Serializable {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T result;

    public ResultVo(int code, String msg, T result) {

    }
    public ResultVo() {

    }

    public void setAll(int code,String msg,T result){
        this.code=code;
        this.msg=msg;
        this.result=result;
    }


}