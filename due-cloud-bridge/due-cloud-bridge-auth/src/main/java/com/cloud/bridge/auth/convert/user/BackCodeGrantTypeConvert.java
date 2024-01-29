package com.cloud.bridge.auth.convert.user;

import com.cloud.bridge.auth.enums.GrantTypeEnum;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 后台用户验证码登录 将手机号码转化成用户信息
 *
 * @author hanwengang
 */
public class BackCodeGrantTypeConvert extends AbstractGrantTypeUsernameConvert {
    @Override
    public boolean support(String code) {
        return GrantTypeEnum.BACK_CODE.getCode().equals(code);
    }

    @Override
    public UserDetails convert(String username) {
        return this.dueUserDetailService.loadUserByPhoneNumber(username);
    }
}
