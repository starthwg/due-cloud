package com.due.basic.tookit.oauth.user;

import com.due.basic.tookit.oauth.CustomAuthorityDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 系统基础用户信息
 */
public interface DueBasicUser extends UserDetails {

    /**
     * 获取用户ID
     *
     * @return Long类型的用户ID
     */
    Long getMemberId();


    @Override
    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}
