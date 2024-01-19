package com.due.cloud.service.back.service.security.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.service.back.domian.reuqest.SystemRoleQuery;
import com.due.cloud.service.back.integration.ISecurityIntegration;
import com.due.cloud.service.back.service.security.ISystemRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SystemRoleServiceImpl implements ISystemRoleService {


    private final ISecurityIntegration securityIntegration;

    @Override
    public List<SystemRole> selectSystemRoleListByPath(String path) {
        LogicAssert.isBlank(path, ErrorEnum.PARAMETER_ERROR);
        Result<List<SystemRole>> result = securityIntegration.getSystemRoleByPath(path);
        result.whenFailureThenThrowException();
        return result.getData();
    }

    @Override
    public List<SystemRole> selectSystemRoleListCondition(SystemRoleQuery query) {
        if (null == query) query = new SystemRoleQuery();
        Result<List<SystemRole>> result = securityIntegration.getSystemRoleListCondition(query);
        result.whenFailureThenThrowException();
        return result.getData();
    }
}
