package com.due.basic.tookit.enums;

/**
 * 渠道类型
 */
public enum ChannelEnum {

    /**
     * app端
     */
    APP(1, "app"),
    WX_APP(2, "微信小程序"),
    BACK(3, "后台");


    ChannelEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
