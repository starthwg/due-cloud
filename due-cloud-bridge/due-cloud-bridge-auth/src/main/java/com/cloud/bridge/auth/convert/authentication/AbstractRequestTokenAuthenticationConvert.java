package com.cloud.bridge.auth.convert.authentication;

import com.cloud.bridge.auth.AuthDueAuthentication;
import com.cloud.bridge.auth.grant.TokenRequest;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequestTokenAuthenticationConvert implements RequestTokenAuthenticationConvert {
    @Override
    public Authentication convert(TokenRequest request) {
        if (null == request) {
            throw new IllegalArgumentException("request params Cannot be null");
        }
        Map<String, Object> params = request.getParams();
        if (null == params) {
            params = new HashMap<>(2);
        }
        Authentication convert = this.toConvert(params);
        if (convert instanceof AuthDueAuthentication) {
            AuthDueAuthentication authentication = (AuthDueAuthentication) convert;
            authentication.setTokenRequest(request);
        }
        return convert;
    }


    public abstract Authentication toConvert(Map<String, Object> params);
}
