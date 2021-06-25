package com.example.eunm;

/**
 * opt枚举
 */
public enum OptEunm {
    /**
     * 登录
     */
    LOGIN(0,"login"),
    /**
     * 注册
     */
    REGISTER(1,"register"),
    /**
     * 排行榜
     */
    GET_PAGE_HONOR(2,"get_page_honor"),
    /**
     *更新荣誉值
     */
    CHANGE_HONOR(3,"change_honor")
    ;;


    private final int code;
    private final String  note;

    public int getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }

    OptEunm(int code, String note) {
        this.code = code;
        this.note = note;
    }
    //重写toString方法，返回值为显示的值
    @Override
    public String toString() {
        return this.getCode()+" " +this.getNote();
    }


}
