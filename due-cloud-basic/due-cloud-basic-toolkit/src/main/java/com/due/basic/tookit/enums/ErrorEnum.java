package com.due.basic.tookit.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ErrorEnum {

    /**
     * 成功
     */
    SUCCESS(200, "OK"),

    // 服务类
    SERVICE_INVALID(1001, "服务调用无效"),
    SERVICE_ERROR(1002, "服务调用异常"),
    SERVICE_ABSENT(1003, "服务不存在"),
    SERVICE_REQUEST_UNKNOWN(1004, "未知服务调用"),
    SERVICE_RESPONSE_UNKNOWN(1005, "未知服务响应"),
    SERVICE_SCENE_UNKNOWN(1006, "未知服务场景"),

    // 参数类
    PARAMETER_INVALID(2001, "参数传入无效"),
    PARAMETER_ERROR(2002, "参数传入异常"),

    // 数据类
    DATA_ABSENT(3001, "数据不存在"),
    DATA_HANDLE_ERROR(3002, "数据处理异常"),
    DATA_EXIST(3003, "数据已存在"),
    DATA_STATUS_ERROR(3004, "数据状态异常"),


    //认证方面
    // 认证未通过
    AUTHORIZE_INVALID(4300, "认证未通过"),
    // 没有权限
    AUTHORIZE_ACCESS_DENIED(4301, "没有权限"),
    // 用户名或密码验证失败
    AUTHORIZE_INVALID_GRANT(4302, "用户名或密码验证失败"),
    // 令牌无效
    AUTHORIZE_TOKEN_INVALID(4303, "令牌无效"),
    // 令牌过期
    AUTHORIZE_TOKEN_EXPIRED(4304, "令牌过期"),
    // 缺少必要参数
    AUTHORIZE_INVALID_PARAMETER(4305, "缺少必要参数"),
    // 续令牌失败
    AUTHORIZE_REFRESH_ERROR(4306, "续令牌失败"),

    other_ERROR(9999999, "其他异常");


    private final Integer code;

    private final String message;

    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorEnum parseByCode(Integer code) {
        if (null == code) {
            return null;
        }
        return Arrays.stream(values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }
}
