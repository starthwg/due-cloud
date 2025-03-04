package com.cloud.bridge.auth.handler;

import com.cloud.bridge.auth.OpenIdNotFoundException;
import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理器
 *
 * @author hanwengang
 */
@Slf4j
public class CustomizeAuthenticationFailed implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        ObjectMapper mapper = new ObjectMapper();
        Result<Object> result = Result.failure(ErrorEnum.AUTHORIZE_INVALID);
        if (exception instanceof BadCredentialsException) {
            result = Result.failure(ErrorEnum.AUTHORIZE_INVALID_GRANT);
        } else if (exception instanceof LockedException || exception instanceof AccountExpiredException || exception instanceof DisabledException) {
            // 账户被锁定
            result = Result.failure(ErrorEnum.AUTHORIZE_LOCK);
        } else if (exception instanceof UsernameNotFoundException) {
            // 账户密码相关的
            result = Result.failure(ErrorEnum.AUTHORIZE_INVALID_GRANT);
        } else if (exception instanceof OpenIdNotFoundException) {
            result = Result.failure(ErrorEnum.AUTHORIZE_NOT_OPEN_ID);
        }
        //允许使用未带引号的字段名
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //允许使用单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.writeValue(response.getOutputStream(), result);
    }
}
