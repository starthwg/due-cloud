package com.due.cloud.service.back.integration.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import com.due.basic.tookit.rpc.DueRpcInterceptor;
import com.due.basic.tookit.rpc.DueRpcService;
import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.rpc.security.service.ISystemRoleService;
import com.due.cloud.service.back.integration.ISecurityIntegration;

import javax.annotation.Resource;
import java.util.List;

@DueRpcService
@DueRpcInterceptor(request = ServiceCodeEnum.BACK, response = ModuleCodeEnum.SECURITY)
public class SecurityIntegrationImpl implements ISecurityIntegration {

    @Resource
    private ISystemRoleService systemRoleService;

    @DueRpcInterceptor(scene = ModuleServiceScene.SELECT)
    @Override
    public Result<List<SystemRole>> getSystemRoleByPath(String path) {
        return systemRoleService.selectRoleListByPath(path);
    }
}
