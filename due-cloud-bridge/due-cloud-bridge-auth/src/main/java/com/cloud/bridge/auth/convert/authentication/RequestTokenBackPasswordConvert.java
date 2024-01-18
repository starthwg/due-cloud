package com.cloud.bridge.auth.convert.authentication;

import com.cloud.bridge.auth.BackPasswordAuthenticationAuth;
import com.cloud.bridge.auth.enums.GrantTypeEnum;
import com.cloud.bridge.auth.grant.TokenRequest;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 后台用户使用password认证方式
 *
 * @author hanwengang
 */
@Component
public class RequestTokenBackPasswordConvert implements RequestTokenAuthenticationConvert {
    @Override
    public boolean support(String code) {
        return GrantTypeEnum.BACK_PASSWORD.getCode().equals(code);
    }

    @Override
    public BackPasswordAuthenticationAuth convert(TokenRequest request) {
        if (null == request) {
            throw new IllegalArgumentException("request params Cannot be null");
        }
        Map<String, Object> params = request.getParams();
        if (null == params) {
            params = new HashMap<>(2);
        }

        String username = Optional.ofNullable(params.get(GlobalAuthConstant.USERNAME)).map(Object::toString).orElse(null);
        String password = Optional.ofNullable(params.get(GlobalAuthConstant.PASSWORD)).map(Object::toString).orElse(null);
        return new BackPasswordAuthenticationAuth(request.getGrantType(), request, username, password);
    }
}
