package com.cloud.bridge.auth.convert.user;

import com.cloud.bridge.auth.enums.GrantTypeEnum;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


/**
 *  后台用户登录将用户名转化成用户信息
 * @author hanwengang
 */
@Component
public class BackPasswordGrantTypeConvert extends AbstractGrantTypeUsernameConvert {
    @Override
    public boolean support(String code) {
        return GrantTypeEnum.BACK_PASSWORD.getCode().equals(code);
    }

    @Override
    public UserDetails convert(String username) {
        return this.dueUserDetailService.loadUserByUsername(username);
    }
}
