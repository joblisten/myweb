package com.example.eunm;

public enum StatesEunm {
    /**
     * 响应枚举
     */
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    NAME_EXSIT(2,"名字重复");
    ;


    private final int code;
    private final String msg;

    private StatesEunm(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    //重写toString方法，返回值为显示的值
    @Override
    public String toString() {
        return this.getMsg();
    }

    //通过code获取结果
    public static StatesEunm fromCode(Integer code) {
        for (StatesEunm status : StatesEunm.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    //通过名称来获取结果
    public static StatesEunm fromName(String msg) {
        for (StatesEunm status : StatesEunm.values()) {
            if (status.getMsg().equals(msg)) {
                return status;
            }
        }
        return null;
    }

    //通过enumName获取结果
    public static StatesEunm fromEnumName(String name) {
        for (StatesEunm status : StatesEunm.values()) {
            if (status.name().equals(name)) {
                return status;
            }
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}