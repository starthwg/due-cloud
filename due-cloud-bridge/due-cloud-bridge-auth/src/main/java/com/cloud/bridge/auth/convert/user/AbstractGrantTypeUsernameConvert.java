package com.cloud.bridge.auth.convert.user;

import com.cloud.bridge.auth.service.DueUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * 将用户名转化成用户信息的基类
 * @author hanwengang
 */
public abstract class AbstractGrantTypeUsernameConvert implements GrantTypeUsernameCovert {

    @Autowired
    protected DueUserDetailService dueUserDetailService;
}
