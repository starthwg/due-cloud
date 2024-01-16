package com.due.cloud.rpc.security.service;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.module.security.domain.response.SystemRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 系统用户服务
 *
 * @author hanwengang
 */
@FeignClient(name = "ModuleSecurity")
public interface ISystemMemberService {

    /**
     * 通过用户名称获取用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    @GetMapping("/security/member/username")
    Result<SystemMember> getSystemMemberByUsername(@RequestParam("username") String username);

    /**
     *  通过用户ID获取用户
     * @param memberId long类型的用户ID
     * @return Result 类型
     */
    @GetMapping("/security/role/member-id")
    Result<List<SystemRole>> getSystemRoleListByMemberId(@RequestParam("memberId") Long memberId);
}
