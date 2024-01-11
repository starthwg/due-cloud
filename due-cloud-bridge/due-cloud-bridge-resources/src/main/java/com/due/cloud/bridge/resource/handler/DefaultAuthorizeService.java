package com.due.cloud.bridge.resource.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认的授权处理
 *
 * @author hanwengang
 */
@Slf4j
public class DefaultAuthorizeService implements AuthorizeService {
    @Override
    public boolean access(HttpServletRequest request, Authentication authentication) {
        String url = request.getRequestURI();
        if (null == authentication || authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated()) {
            log.warn("没有登录，或者匿名用户不能访问的路径：{}", url);
            return false;
        }
        if (!authentication.isAuthenticated()) {
            log.warn("没有认证通过,直接返回，访问的的路径：{}", url);
            return false;
        }
        return true;
    }
}
