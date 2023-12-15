package com.due.basic.tookit.doamin;

/**
 * 统一异常枚举
 */
public enum ErrorEnum {


    /**
     * 其他异常信息
     */
    OTHER_ERROR(9999, "其他异常");


    /**
     * 响应编码
     */
    private final int code;

    /**
     * 响应描述
     */
    private final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
