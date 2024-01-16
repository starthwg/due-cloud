package com.due.cloud.service.auth.service;

import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.module.security.domain.response.SystemRole;

import java.util.List;

public interface ISystemMemberService {

    SystemMember getSystemMemberByUsername(String username);

    List<SystemRole> getSystemRoleByMemberId(Long memberId);
}
