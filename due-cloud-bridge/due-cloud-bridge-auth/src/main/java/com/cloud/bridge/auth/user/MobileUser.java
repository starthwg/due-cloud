package com.cloud.bridge.auth.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 *  移动端用户信息
 */
public class MobileUser implements DueBasicUser{

    private Long dataId;

    /**
     *  用户昵称
     */
    private String nickname;

    /**
     *  手机号码
     */
    private String phoneNumber;

    /**
     *  头像
     */
    private Long imageId;

    /**
     *  登录密码
     */
    private String password;

    /**
     *  是否被禁用
     */
    private boolean enabled;

    @Override
    public Long getMemberId() {
        return dataId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
