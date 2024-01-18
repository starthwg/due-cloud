package com.cloud.bridge.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.cloud.bridge.auth.handler.CustomizerDueTokenConvertUserDetailFailedHandler;
import com.cloud.bridge.auth.handler.CustomsizeDueTokenUserDetailSuccessHandler;
import com.cloud.bridge.auth.handler.DueTokenConvertUserDetailFailedHandler;
import com.cloud.bridge.auth.handler.DueTokenUserDetailSuccessHandler;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.oauth.service.DueTokenService;
import com.due.basic.tookit.oauth.service.impl.DueTokenServiceImpl;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.due.basic.tookit.utils.LogicUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 每次请求都回去进过的过滤器
 *
 * @author hanwengang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class TokenConvertUserDetailFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcher;

    /**
     * 处理成功的处理
     */
    private final DueTokenUserDetailSuccessHandler tokenUserDetailSuccessHandler = new CustomsizeDueTokenUserDetailSuccessHandler();


    /**
     * 处理失败的处理
     */
    private final DueTokenConvertUserDetailFailedHandler tokenConvertUserDetailFailedHandler = new CustomizerDueTokenConvertUserDetailFailedHandler();

    /**
     * 将token转化成用户信息的服务
     */
    private final DueTokenService dueTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = this.getToken(request);
            if (LogicUtil.isAllBlank(token)) {
                throw new AuthenticationCredentialsNotFoundException("request header not params token");
            }
            DueBasicUser basicUser = dueTokenService.tokenConvertUserDetail(token);
            if (null == basicUser) {
                throw new BadCredentialsException("Token is invalid");
            }
            log.info("校验的用户ID：{}", basicUser.getMemberId());
            this.successfulAuthentication(request, response, basicUser);
        } catch (AuthenticationException e) {
            log.debug("Failed to verify token");
            this.unsuccessfulAuthentication(request, response, e);
        }

    }


    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (this.requestMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger
                    .trace(LogMessage.format("Did not match request to %s", this.requestMatcher));
        }
        return false;
    }

    /**
     * 认证异常的处理
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.info("解析token认证失败：{}", failed.getMessage());
        tokenConvertUserDetailFailedHandler.onAuthenticationFailure(request, response, failed);
    }

    /**
     * 认证成功的处理
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, DueBasicUser dueBasicUser) throws IOException {
        log.info("解析token成功：{}", JSONObject.toJSONString(dueBasicUser));
        this.tokenUserDetailSuccessHandler.onAuthenticationSuccess(request, response, dueBasicUser);
    }


    protected String getToken(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String token = request.getHeader(GlobalAuthConstant.Authorization);
        if (LogicUtil.isAllBlank(token)) return null;
        token = token.replace(GlobalAuthConstant.BEARER, "");
        return token;
    }
}
