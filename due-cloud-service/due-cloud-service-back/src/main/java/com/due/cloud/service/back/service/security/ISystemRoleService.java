package com.due.cloud.service.back.service.security;

import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.service.back.domian.reuqest.SystemRoleQuery;

import java.util.List;

public interface ISystemRoleService {

    /**
     * 通过资源路径获取角色信息
     *
     * @param path string类型
     * @return list类型
     */
    List<SystemRole> selectSystemRoleListByPath(String path);

    /**
     * 通过条件查询系统角色信息
     *
     * @param query SystemRoleQuery类型的条件
     * @return 返回list类型
     */
    List<SystemRole> selectSystemRoleListCondition(SystemRoleQuery query);
}
