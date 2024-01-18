package com.due.cloud.service.back.config;

import com.due.basic.tookit.oauth.AbstractDueAuthentication;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.cloud.bridge.resource.handler.DefaultAuthorizeService;
import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.service.back.service.security.ISystemRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 基于role的授权
 *
 * @author hanwengang
 */
@Component("authorizeService")
@Slf4j
public class RoleAuthorizeServiceImpl extends DefaultAuthorizeService {

    @Autowired
    private ISystemRoleService systemRoleService;

    @Override
    public boolean access(HttpServletRequest request, Authentication authentication) {
        boolean access = super.access(request, authentication);
        if (access) {
            // 基于角色的认证
            String pathUrl = request.getRequestURI();
            if (LogicUtil.isAllBlank(pathUrl)) {
                return false;
            }
            List<SystemRole> systemRoles = systemRoleService.selectSystemRoleListByPath(pathUrl);
            if (LogicUtil.isEmpty(systemRoles)) {
                return false;
            }
            Set<String> roleCodeSet = systemRoles.stream().map(SystemRole::getCode).collect(Collectors.toSet());
            // 获取认证的角色信息
            if (authentication instanceof AbstractDueAuthentication) {
                AbstractDueAuthentication abstractDueAuthentication = (AbstractDueAuthentication) authentication;
                Collection<? extends GrantedAuthority> authorities = abstractDueAuthentication.getAuthorities();
                if (LogicUtil.isEmpty(authorities)) {
                    return false;
                }
                boolean updated = authorities.stream().anyMatch(e -> roleCodeSet.contains(e.getAuthority()));
                log.info("请求路径:{}, 是否可以访问：{}", pathUrl, updated);
                return updated;
            }
        }
        return false;
    }
}
