package com.due.cloud.service.auth.intergration;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.security.domain.response.SystemMember;

public interface ISecurityIntergration {

    Result<SystemMember> getSystemMemberByUsername(String username);
}
