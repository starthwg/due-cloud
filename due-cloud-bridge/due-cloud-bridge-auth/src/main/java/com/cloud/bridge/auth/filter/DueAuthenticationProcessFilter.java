package com.cloud.bridge.auth.filter;

import com.cloud.bridge.auth.convert.authentication.RequestTokenAuthenticationConvert;
import com.cloud.bridge.auth.grant.TokenRequest;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 认证拦截器
 *
 * @author hanwengang
 */
@Slf4j
public class DueAuthenticationProcessFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTH_URL = "/auth/token";


    private List<RequestTokenAuthenticationConvert> requestTokenAuthenticationConvertList;


    /**
     * token转化器
     */
    private final RequestTokenAuthenticationConvert tokenAuthenticationConvert = new DelegateRequestTokenAuthenticationConvert();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String grantType = request.getParameter(GlobalAuthConstant.GRANT_TYPE);
        if (LogicUtil.isAllBlank(grantType)) {
            throw new AuthenticationCredentialsNotFoundException("The 'GrantType' parameter was not found in the request header");
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> params = new HashMap<>(4);
        parameterMap.forEach((k, v) -> {
            params.put(k, v[0]);
        });
        TokenRequest tokenRequest = this.getTokenRequest(grantType, params, request);
        // 将token转化成认证对象
        Authentication authentication = tokenAuthenticationConvert.convert(tokenRequest);
        ThreadLocalUtil.set(GlobalThreadLocalConstant.SERIAL_NO, UUID.randomUUID().toString());
        return super.getAuthenticationManager().authenticate(authentication);
    }

    public DueAuthenticationProcessFilter() {
        super(new AntPathRequestMatcher(AUTH_URL, HttpMethod.POST.name()));
//        log.info("授权地址：{}， 请求方式：{}", AUTH_URL, HttpMethod.POST.name());
    }

    public DueAuthenticationProcessFilter(List<RequestTokenAuthenticationConvert> requestTokenAuthenticationConvertList) {
        this();
        this.requestTokenAuthenticationConvertList = requestTokenAuthenticationConvertList;
    }

    protected TokenRequest getTokenRequest(String grantType, Map<String, Object> params, HttpServletRequest request) {
        return new TokenRequest(grantType, params, request);
    }


    /**
     * 处理每个请求的token转化器
     */
    private class DelegateRequestTokenAuthenticationConvert implements RequestTokenAuthenticationConvert {
        @Override
        public boolean support(String code) {
            return false;
        }

        @Override
        public Authentication convert(TokenRequest request) {
            return requestTokenAuthenticationConvertList.stream().filter(e -> e.support(request.getGrantType()))
                    .findFirst().map(e -> e.convert(request)).orElse(null);
        }
    }

}
