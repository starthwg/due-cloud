package com.due.cloud.service.auth.service.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.service.auth.intergration.ISecurityIntergration;
import com.due.cloud.service.auth.service.ISystemMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
