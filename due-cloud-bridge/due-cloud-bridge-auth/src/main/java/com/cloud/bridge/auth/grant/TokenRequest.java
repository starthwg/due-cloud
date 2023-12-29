package com.cloud.bridge.auth.grant;


import com.cloud.bridge.auth.enums.GrantTypeEnum;
import com.due.basic.tookit.utils.LogicUtil;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 获取token的请求
 *
 * @author hanwengang
 */
public class TokenRequest extends BasicRequest {


    /**
     * 认证类型
     */
    private String grantType;

    public TokenRequest(String grantType, Map<String, Object> params, HttpServletRequest request) {
        super(params, request);
        this.grantType = grantType;
    }

    public TokenRequest() {
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }


}
