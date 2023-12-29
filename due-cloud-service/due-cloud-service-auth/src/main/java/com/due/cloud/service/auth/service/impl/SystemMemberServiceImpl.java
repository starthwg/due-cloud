package com.due.cloud.service.auth.service.impl;

import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.service.auth.service.ISystemMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hanwengang
 */
@Service
@Slf4j
public class SystemMemberServiceImpl implements ISystemMemberService {
    @Override
    public SystemMember getSystemMemberByUsername(String username) {
        return null;
    }
}
