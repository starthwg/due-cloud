package com.due.cloud.bridge.resource.filter;

import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.utils.LogicUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class DueTokenConvertUserDetailFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断是否有token
        String token = this.getRequestToken(request);
        if (LogicUtil.isAllBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

    }

    /**
     *  通过token获取认证信息
     * @param token string类型的token
     * @return Authentication 认证信息
     */
    public abstract Authentication tokenConvertAuthentication(String token);

    /**
     *  获取请求token中的token信息
     * @param request 请求信息
     * @return token信息
     */
    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader(GlobalAuthConstant.Authorization);
        if (LogicUtil.isAllBlank(token)) return null;
        return token.replace(GlobalAuthConstant.BEARER, "");
    }
}
