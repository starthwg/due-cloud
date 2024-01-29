package com.due.cloud.module.customer.api.domain.enums;


import lombok.Getter;

/**
 * 用户登录类型
 */
@Getter
public enum CustomerAccountTypeEnum {
    /**
     * 微信openId
     */
    WX_OPENID(1, "微信OpenId", ""),

    /**
     * 账户密码
     */
    ACCOUNT_PASSWORD(2, "账户密码", "");


    private final int code;

    private final String name;

    private final String color;

    CustomerAccountTypeEnum(int code, String name, String color) {
        this.code = code;
        this.name = name;
        this.color = color;
    }
}
