package com.due.cloud.bridge.resource.handler;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 *  授权服务
 * @author hwg
 */
public interface AuthorizeService {

    /**
     *  认证处理
     * @param request 请求信息
     * @param authentication 认证信息
     * @return true 可以访问 false 无法访问
     */
    boolean access(HttpServletRequest request, Authentication authentication);
}
