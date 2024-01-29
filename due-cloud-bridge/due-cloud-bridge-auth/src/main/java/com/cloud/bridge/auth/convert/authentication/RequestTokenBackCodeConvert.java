package com.cloud.bridge.auth.convert.authentication;

import com.cloud.bridge.auth.BackCodeAuthenticationAuth;
import com.cloud.bridge.auth.enums.GrantTypeEnum;
import com.cloud.bridge.auth.grant.TokenRequest;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 后台验证码模式
 *
 * @author hanwengang
 */
public class RequestTokenBackCodeConvert extends AbstractRequestTokenAuthenticationConvert {

    @Override
    public Authentication toConvert(Map<String, Object> params) {
        String phoneNumber = Optional.ofNullable(params.get(GlobalAuthConstant.PHONE_NUMBER)).map(Object::toString).orElse(null);
        String code = Optional.ofNullable(params.get(GlobalAuthConstant.CODE)).map(Object::toString).orElse(null);
        return new BackCodeAuthenticationAuth(GrantTypeEnum.BACK_CODE.getCode(), code, phoneNumber);
    }

    @Override
    public boolean support(String code) {
        return GrantTypeEnum.BACK_CODE.getCode().equals(code);
    }
}
