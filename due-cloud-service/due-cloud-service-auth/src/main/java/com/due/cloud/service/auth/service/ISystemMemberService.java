package com.due.cloud.service.auth.service;

import com.due.cloud.module.security.domain.response.SystemMember;

public interface ISystemMemberService {

    SystemMember getSystemMemberByUsername(String username);
}
