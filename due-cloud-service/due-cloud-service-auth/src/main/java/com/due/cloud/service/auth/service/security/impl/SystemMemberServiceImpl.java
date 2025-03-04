package com.due.cloud.service.auth.service.security.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.service.auth.intergration.ISecurityIntergration;
import com.due.cloud.service.auth.service.security.ISystemMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hanwengang
 */
@Service
@Slf4j
public class SystemMemberServiceImpl implements ISystemMemberService {

    @Autowired
    private ISecurityIntergration securityIntergration;

    @Override
    public SystemMember getSystemMemberByUsername(String username) {
        Result<SystemMember> memberResult = securityIntergration.getSystemMemberByUsername(username);
        memberResult.whenFailureThenThrowException();
        return memberResult.getData();
    }

    @Override
    public List<SystemRole> getSystemRoleByMemberId(Long memberId) {
        Result<List<SystemRole>> result = securityIntergration.getSystemRoleByMemberId(memberId);
        result.whenFailureThenThrowException();
        return result.getData();
    }
}
