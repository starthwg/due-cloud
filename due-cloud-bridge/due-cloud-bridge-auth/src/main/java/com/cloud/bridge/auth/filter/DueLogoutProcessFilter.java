package com.cloud.bridge.auth.filter;

import com.due.basic.tookit.oauth.service.DueTokenService;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  退出登录的处理器器
 * @author hanwengang
 */
public class DueLogoutProcessFilter extends OncePerRequestFilter {


    private final DueTokenService dueTokenService;

    private final RequestMatcher requestMatcher;

    public DueLogoutProcessFilter(DueTokenService dueTokenService, String logOutUrl) {
        this.dueTokenService = dueTokenService;
        this.requestMatcher = new AntPathRequestMatcher(logOutUrl, HttpMethod.POST.name());

    }

    public DueLogoutProcessFilter(DueTokenService dueTokenService, RequestMatcher requestMatcher) {
        this.dueTokenService = dueTokenService;
        this.requestMatcher = requestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (! this.requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 准备删除token
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
}
