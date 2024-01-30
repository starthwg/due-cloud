package com.cloud.bridge.auth.convert.authentication;

import com.cloud.bridge.auth.WeChatAuthenticationAuth;
import com.cloud.bridge.auth.enums.GrantTypeEnum;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.enums.ChannelEnum;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RequestTokenMobileOpenIdConvert extends AbstractRequestTokenAuthenticationConvert {
    @Override
    public boolean support(String code) {
        return GrantTypeEnum.MOBILE_WECHAT_OPEN_ID.getCode().equals(code);
    }

    @Override
    public Authentication toConvert(Map<String, Object> params) {
        WeChatAuthenticationAuth weChatAuthenticationAuth = new WeChatAuthenticationAuth();
        weChatAuthenticationAuth.setOpenId((String) params.get(GlobalAuthConstant.OPENID));
        weChatAuthenticationAuth.setGrantType(GrantTypeEnum.MOBILE_WECHAT_OPEN_ID.getCode());

        // 设置渠道类型
        ThreadLocalUtil.set(GlobalThreadLocalConstant.CHANNEL_ENUM, ChannelEnum.APP);
        return weChatAuthenticationAuth;
    }

}
