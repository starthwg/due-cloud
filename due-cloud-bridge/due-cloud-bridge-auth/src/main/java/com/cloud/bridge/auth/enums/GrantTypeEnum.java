package com.cloud.bridge.auth.enums;

import com.due.basic.tookit.utils.LogicUtil;

import java.util.Arrays;

/**
 * grantType类型
 *
 * @author Administrator
 */
public enum GrantTypeEnum {
    /**
     * 后台用户账号密码登录
     */
    BACK_PASSWORD("back_password", "后端用户账户密码登录"),

    BACK_CODE("back_code", "后台手机验证码登录"),

    /**
     * 移动端微信小程序登录
     */
    MOBIEL_WeChat_OPEN_ID("mobile_wechat_open_id", "移动端微信openId登录"),

    /**
     * 移动端账户密码登录
     */
    MOBILE_PASSWORD("mobile_password", "移动端账号密码登录"),

    /**
     * 移动端手机验证码登录
     */
    MOBILE_CODE("mobile_code", "移动端手机严重码登录");


    /**
     * 类型
     */
    private final String code;


    /**
     * 描述
     */
    private final String desc;

    GrantTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static GrantTypeEnum covertCode(String code) {
        if (LogicUtil.isAllBlank(code)) return null;
        return Arrays.stream(values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
