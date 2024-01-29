package com.cloud.bridge.auth.convert.authentication;

import com.cloud.bridge.auth.BackPasswordAuthenticationAuth;
import com.cloud.bridge.auth.enums.GrantTypeEnum;
import com.cloud.bridge.auth.grant.TokenRequest;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 后台用户使用password认证方式
 *
 * @author hanwengang
 */

public class RequestTokenBackPasswordConvert extends AbstractRequestTokenAuthenticationConvert {
    @Override
    public boolean support(String code) {
        return GrantTypeEnum.BACK_PASSWORD.getCode().equals(code);
    }


    @Override
    public Authentication toConvert(Map<String, Object> params) {
        String username = Optional.ofNullable(params.get(GlobalAuthConstant.USERNAME)).map(Object::toString).orElse(null);
        String password = Optional.ofNullable(params.get(GlobalAuthConstant.PASSWORD)).map(Object::toString).orElse(null);
        return new BackPasswordAuthenticationAuth(GrantTypeEnum.BACK_PASSWORD.getCode(),  username, password);
    }
}
