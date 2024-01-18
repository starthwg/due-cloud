package com.due.cloud.service.back.service.security;

import com.due.cloud.module.security.domain.response.SystemRole;

import java.util.List;

public interface ISystemRoleService {

    /**
     *  通过资源路径获取角色信息
     * @param path string类型
     * @return list类型
     */
    List<SystemRole> selectSystemRoleListByPath(String path);
}
