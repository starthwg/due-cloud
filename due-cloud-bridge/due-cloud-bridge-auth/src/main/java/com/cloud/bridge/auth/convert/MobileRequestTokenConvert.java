package com.cloud.bridge.auth.convert;

import com.cloud.bridge.auth.enums.UserTypeEnum;
import com.cloud.bridge.auth.user.MobileUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 移动端转化器
 * @author hanwengang
 */
public class MobileRequestTokenConvert implements RequestTokenConvert<MobileUser> {
    @Override
    public MobileUser convert(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean support(int code) {
        return UserTypeEnum.MOBILE.equals(UserTypeEnum.resolveCode(code));
    }
}
