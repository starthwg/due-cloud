package com.cloud.bridge.auth.handler;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  token转user成功处理器
 * @author hanwengang
 */
public interface DueTokenUserDetailSuccessHandler {


    /**
     *  认证成功的处理
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     */
    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException;
}
