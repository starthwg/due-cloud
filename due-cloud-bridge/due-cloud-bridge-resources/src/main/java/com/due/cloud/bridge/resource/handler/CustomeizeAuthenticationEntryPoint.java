package com.due.cloud.bridge.resource.handler;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomeizeAuthenticationEntryPoint implements AuthenticationEntryPoint, DueResponse{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        log.info("访问的url:{}, 认证未通过。", requestURI);
        this.responseError(response, Result.failure(ErrorEnum.AUTHORIZE_ACCESS_DENIED), HttpServletResponse.SC_UNAUTHORIZED);
    }
}
