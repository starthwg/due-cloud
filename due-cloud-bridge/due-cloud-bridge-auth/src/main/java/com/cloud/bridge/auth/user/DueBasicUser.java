package com.cloud.bridge.auth.user;

import org.springframework.security.core.userdetails.UserDetails;

/**
 *  系统基础用户信息
 */
public interface DueBasicUser extends UserDetails {

    /**
     *  获取用户ID
     * @return Long类型的用户ID
     */
    Long getMemberId();
}
