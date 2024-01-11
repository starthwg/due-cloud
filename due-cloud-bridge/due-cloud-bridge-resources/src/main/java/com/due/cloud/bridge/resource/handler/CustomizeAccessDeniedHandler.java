package com.due.cloud.bridge.resource.handler;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler, DueResponse {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        log.info("访问的url:{}, 认证未通过。", requestURI);
        this.responseError(response, Result.failure(ErrorEnum.AUTHORIZE_ACCESS_DENIED), HttpServletResponse.SC_UNAUTHORIZED);
    }
}