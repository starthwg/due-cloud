package com.cloud.bridge.auth.enums;

import java.util.Arrays;

/**
 * 用户类型
 */
public enum UserTypeEnum {

    /**
     * 哟东段
     */
    MOBILE(1, "移动端"),
    BACK(2, "后台");


    UserTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UserTypeEnum resolveCode(Integer code) {
        if (null == code) return null;
        return Arrays.stream(values()).filter(e -> e.getCode() == code).findFirst().orElse(null);
    }
}
