package com.cloud.bridge.auth.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证拦截器
 *
 * @author hanwengang
 */
public class DueAuthenticationProcessFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTH_URL = "/auth/token";


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        return null;
    }

    protected DueAuthenticationProcessFilter() {
        super("/auth/token");
    }
}
