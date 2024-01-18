package com.due.cloud.service.auth.intergration;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.module.security.domain.response.SystemRole;

import java.util.List;

/**
 * @author Administrator
 */
public interface ISecurityIntergration {

    Result<SystemMember> getSystemMemberByUsername(String username);

    /**
     *  通过用户ID获取用户对的权限信息
     * @param memberId Long类型
     * @return result类型
     */
    Result<List<SystemRole>> getSystemRoleByMemberId(Long memberId);
}
