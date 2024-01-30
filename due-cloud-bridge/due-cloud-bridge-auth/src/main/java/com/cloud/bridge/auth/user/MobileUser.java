package com.cloud.bridge.auth.user;

import com.alibaba.fastjson2.annotation.JSONField;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 *  移动端用户信息
 */
public class MobileUser implements DueBasicUser {

    private Long dataId;

    /**
     *  openId
     */
    private String openId;

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


    @JSONField(serialize = false)
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public MobileUser() {
    }

    public MobileUser(Long dataId, String openId, String nickname, String phoneNumber, Long imageId, String password, boolean enabled) {
        this.dataId = dataId;
        this.openId = openId;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.imageId = imageId;
        this.password = password;
        this.enabled = enabled;
    }

}
