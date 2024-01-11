package com.cloud.bridge.auth.handler;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token转userDetail失败的处理器
 * @author hanwengang认证失败的处理
 */
public interface DueTokenConvertUserDetailFailedHandler {

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException;
}
