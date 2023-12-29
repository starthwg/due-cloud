package com.due.basic.tookit.enums;

public enum ServiceCodeEnum {

    /**
     * 用户服务
     */
    BACK(2, "后台服务"),

    AUTH(3, "认证服务"),
    UNKNOWN(9999, "未知服务"),

    MOBILE(1, "移动端服务");

    /**
     * 编码
     */
    private final int code;


    /**
     * 描述
     */
    private final String desc;

    ServiceCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
