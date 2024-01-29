package com.cloud.bridge.auth.convert.user;

import com.cloud.bridge.auth.enums.GrantTypeEnum;
import com.due.basic.tookit.utils.LogicUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MobileOpenIdGrantTypeConvert extends AbstractGrantTypeUsernameConvert {
    @Override
    public boolean support(String code) {
        return GrantTypeEnum.MOBILE_WECHAT_OPEN_ID.getCode().equals(code);
    }

    @Override
    public UserDetails convert(String username) {
        if (LogicUtil.isAllBlank(username)) {
            throw new UsernameNotFoundException("openId不能为null！");
        }
        return this.dueUserDetailService.loadUserByOpenId(username);
    }
}
