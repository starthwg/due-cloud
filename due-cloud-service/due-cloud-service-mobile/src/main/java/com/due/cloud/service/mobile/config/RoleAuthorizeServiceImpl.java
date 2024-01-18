package com.due.cloud.service.mobile.config;

import com.due.cloud.bridge.resource.handler.AuthorizeService;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

//@Component("authorizeService")
public class RoleAuthorizeServiceImpl implements AuthorizeService {
    @Override
    public boolean access(HttpServletRequest request, Authentication authentication) {
        return false;
    }
}
