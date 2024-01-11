package com.due.basic.tookit.oauth.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author hanwengang
 */
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
     * 权限信息
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 密码
     */
    private String password;


    /**
     * 手机号码
     */
    private String mobile;



    @Override
    public Long getMemberId() {
        return this.dataId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BackUser() {
    }

    public BackUser(Long dataId, String username, boolean enabled, Collection<? extends GrantedAuthority> authorities, String password, String mobile) {
        this.dataId = dataId;
        this.username = username;
        this.enabled = enabled;
        this.authorities = authorities;
        this.password = password;
        this.mobile = mobile;
    }
}
