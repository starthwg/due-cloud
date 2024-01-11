package com.due.cloud.service.auth.intergration.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import com.due.basic.tookit.rpc.RpcInterceptor;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.rpc.security.service.ISystemMemberService;
import com.due.cloud.service.auth.intergration.ISecurityIntergration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
@RpcInterceptor(request = ServiceCodeEnum.AUTH, response = ModuleCodeEnum.SECURITY)
public class SecurityIntergrationImpl implements ISecurityIntergration {

    @Resource
    private ISystemMemberService systemMemberService;


    @RpcInterceptor(scene = ModuleServiceScene.SELECT)
    @Override
    public Result<SystemMember> getSystemMemberByUsername(String username) {
        return systemMemberService.getSystemMemberByUsername(username);
    }
}
