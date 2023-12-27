package com.cloud.bridge.auth.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BackUser implements DueBasicUser {

    /**
     * 用户ID
     */
    private Long dataId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 是否被禁用
     */
    private boolean enabled;

    /**
     *  权限信息
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     *  密码
     */
    private String password;


    @Override
    public Long getMemberId() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
