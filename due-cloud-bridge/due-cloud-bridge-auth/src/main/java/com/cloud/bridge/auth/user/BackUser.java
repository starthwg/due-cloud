package com.cloud.bridge.auth.user;

import com.alibaba.fastjson2.annotation.JSONField;
import com.due.basic.tookit.oauth.CustomAuthorityDeserializer;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.due.basic.tookit.utils.LogicUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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
     * 角色信息
     */
    private Collection<String> roles;

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
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (LogicUtil.isEmpty(roles)) return Collections.emptyList();
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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

    public BackUser(Long dataId, String username, boolean enabled, Collection<String> roles, String password, String mobile) {
        this.dataId = dataId;
        this.username = username;
        this.enabled = enabled;
        this.roles = roles;
        this.password = password;
        this.mobile = mobile;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }
}
