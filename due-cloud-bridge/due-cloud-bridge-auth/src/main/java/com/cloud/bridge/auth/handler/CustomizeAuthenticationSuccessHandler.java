package com.cloud.bridge.auth.handler;

import com.cloud.bridge.auth.DueAuthentication;
import com.cloud.bridge.auth.service.DueTokenService;
import com.cloud.bridge.auth.service.impl.DueTokenServiceImpl;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.oauth.Token;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证成功处理器器
 *
 * @author hanwengang
 */

@Slf4j
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public CustomizeAuthenticationSuccessHandler(DueTokenService dueTokenService) {
        this.dueTokenService = dueTokenService;
    }

    private final DueTokenService dueTokenService ;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("认证成功！");
        if (authentication instanceof DueAuthentication) {
            DueAuthentication dueAuthentication = (DueAuthentication) authentication;
            DueBasicUser dueBasicUser = dueAuthentication.getDueBasicUser();
            log.info("认证成功的用户:{}", dueBasicUser);
            Token token = dueTokenService.createToken(dueBasicUser);
            LogicAssert.isNull(token, ErrorEnum.DATA_HANDLE_ERROR);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            ObjectMapper mapper = new ObjectMapper();
            //允许使用未带引号的字段名
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            //允许使用单引号
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.writeValue(response.getOutputStream(), token);
        }
    }
}
