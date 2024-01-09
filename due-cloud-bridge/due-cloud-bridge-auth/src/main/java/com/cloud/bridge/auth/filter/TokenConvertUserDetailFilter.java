package com.cloud.bridge.auth.filter;

import com.cloud.bridge.auth.handler.DueTokenConvertUserDetailFailedHandler;
import com.cloud.bridge.auth.handler.DueTokenUserDetailSuccessHandler;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.utils.LogicUtil;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 每次请求都回去进过的过滤器
 */
public class TokenConvertUserDetailFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcher;

    /**
     * 处理成功的处理
     */
    private DueTokenUserDetailSuccessHandler tokenUserDetailSuccessHandler;

    /**
     * 处理失败的处理
     */
    private DueTokenConvertUserDetailFailedHandler tokenConvertUserDetailFailedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
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

    protected String getToken(HttpServletRequest request) {
        if (null == request) return null;
        String token = request.getHeader(GlobalAuthConstant.BEARER);
        if (LogicUtil.isAllBlank(token)) {
            token = request.getHeader(GlobalAuthConstant.BEARER.toLowerCase());
        }
        return token;
    }
}
