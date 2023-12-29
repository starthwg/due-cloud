package com.due.basic.tookit.enums;

public enum ModuleCodeEnum {

    /**
     * 用户服务
     */
    CUSTOMER(10, "用户模块"),

    SECURITY(11, "安全模块"),
    UNKNOWN(9999, "未知模块");

    /**
     * 编码
     */
    private final int code;


    /**
     * 描述
     */
    private final String desc;

    ModuleCodeEnum(int code, String desc) {
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
