package com.due.cloud.rpc.security.service;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ModuleSecurity")
public interface ISystemRoleService {

    /**
     *  通过资源获取对应的角色信息
     * @param path string类型的
     * @return list类型
     */
    @GetMapping("/security/role/path")
    Result<List<SystemRole>> selectRoleListByPath(@RequestParam("path") String path);
}
