package com.cloud.bridge.auth.convert.authentication;

import com.cloud.bridge.auth.convert.RequestTokenConvert;
import com.cloud.bridge.auth.grant.TokenRequest;
import org.springframework.security.core.Authentication;

/**
 * 将请求转化成认证对象
 *
 *
 * @author hanwengang
 */
public interface RequestTokenAuthenticationConvert extends RequestTokenConvert {

    /**
     * 转化
     *
     * @param request token请求对象
     * @return Authentication 类型的认证对象
     */
    Authentication convert(TokenRequest request);

}
