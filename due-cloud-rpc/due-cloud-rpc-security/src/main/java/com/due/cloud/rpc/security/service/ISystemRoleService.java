package com.due.cloud.rpc.security.service;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.request.SelectSystemRole;
import com.due.cloud.module.security.domain.response.SystemRole;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ModuleSecurity")
public interface ISystemRoleService {

    /**
     * 通过资源获取对应的角色信息
     *
     * @param path string类型的
     * @return list类型
     */
    @GetMapping("/security/role/path")
    Result<List<SystemRole>> selectRoleListByPath(@RequestParam("pathUrl") String path);


    /**
     * 通过条件获取系统角色
     *
     * @param role 条件
     * @return list类型
     */
    @GetMapping("/security/role/list/condition")
    Result<List<SystemRole>> selectRoleListCondition( @SpringQueryMap SelectSystemRole role);
}
