package com.cloud.bridge.auth.filter;

import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.oauth.service.DueTokenService;
import com.due.basic.tookit.utils.LogicUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 退出登录的处理器器
 *
 * @author hanwengang
 */
public class DueLogoutProcessFilter extends OncePerRequestFilter {


    private static final String AUTH_URL = "/auth/logout";

    private final DueTokenService dueTokenService;

    private final RequestMatcher requestMatcher;

    public DueLogoutProcessFilter(DueTokenService dueTokenService, String logOutUrl) {
        this.dueTokenService = dueTokenService;
        if (LogicUtil.isAllBlank(logOutUrl)) {
            logOutUrl = AUTH_URL;
        }
        this.requestMatcher = new AntPathRequestMatcher(logOutUrl, HttpMethod.POST.name());

    }

    public DueLogoutProcessFilter(DueTokenService dueTokenService, RequestMatcher requestMatcher) {
        this.dueTokenService = dueTokenService;
        if (null == requestMatcher) {
            requestMatcher = new AntPathRequestMatcher(AUTH_URL, HttpMethod.POST.name());
        }
        this.requestMatcher = requestMatcher;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 准备删除token
        String token = request.getHeader(GlobalAuthConstant.Authorization);
        if (LogicUtil.isAllBlank(token)) {
            this.execResponse(response, HttpStatus.BAD_REQUEST, "authorization parameter cannot be empty");
            return;
        }
        token = token.replace(GlobalAuthConstant.BEARER, "");
        dueTokenService.removeToken(token);
        this.execResponse(response, HttpStatus.OK, "Ok");
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

    protected void execResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        ObjectMapper mapper = new ObjectMapper();
        //允许使用未带引号的字段名
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //允许使用单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.writeValue(response.getOutputStream(), data);
    }
}
