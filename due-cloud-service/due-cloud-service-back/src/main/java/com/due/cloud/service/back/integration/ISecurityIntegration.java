package com.due.cloud.service.back.integration;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemRole;

import java.util.List;

public interface ISecurityIntegration {

    /**
     *  通过资源路径获取对应的角色列表
     * @param path string类型的资源路径
     * @return list类型
     */
    Result<List<SystemRole>> getSystemRoleByPath(String path);
}
