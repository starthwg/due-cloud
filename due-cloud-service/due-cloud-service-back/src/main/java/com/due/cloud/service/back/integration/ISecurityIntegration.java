package com.due.cloud.service.back.integration;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.service.back.domian.reuqest.SystemRoleQuery;

import javax.sound.midi.SoundbankResource;
import java.util.List;

public interface ISecurityIntegration {

    /**
     *  通过资源路径获取对应的角色列表
     * @param path string类型的资源路径
     * @return list类型
     */
    Result<List<SystemRole>> getSystemRoleByPath(String path);

    /**
     *  通过条件查询系统角色信息
     * @param query 条件
     * @return list类型
     */
    Result<List<SystemRole>> getSystemRoleListCondition(SystemRoleQuery query);
}
