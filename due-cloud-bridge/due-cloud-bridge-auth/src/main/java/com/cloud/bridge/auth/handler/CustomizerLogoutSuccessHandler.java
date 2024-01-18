package com.cloud.bridge.auth.handler;

import com.due.basic.tookit.oauth.service.DueTokenService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  自定义退出登录处理器器
 * @author 退出登录的地址
 */
@Data
@Slf4j
public class CustomizerLogoutSuccessHandler implements LogoutSuccessHandler {


    private final DueTokenService dueTokenService;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("准备退出登录");
        
    }
}
