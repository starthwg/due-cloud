package com.cloud.bridge.auth;

import com.cloud.bridge.auth.grant.TokenRequest;
import com.cloud.bridge.auth.user.BackUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 后台用户编码认证对象
 *
 * @author hanwengang
 */
public class BackCodeAuthentication extends DueAuthentication {


    /**
     * 验证码
     */
    private String code;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户详情信息
     */
    private BackUser backUser;

    public BackCodeAuthentication(String grantType, String code, String phoneNumber) {
        super(grantType);
        this.code = code;
        this.phoneNumber = phoneNumber;
    }

    public BackCodeAuthentication(String grantType, TokenRequest tokenRequest, String code, String phoneNumber) {
        super(grantType, tokenRequest);
        this.code = code;
        this.phoneNumber = phoneNumber;
    }

    public BackCodeAuthentication(String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities, Long dataId, String code, String phoneNumber, BackUser backUser) {
        super(dataId, grantType, tokenRequest, authenticated, backUser, authorities);
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.backUser = backUser;
    }

    public BackCodeAuthentication() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getDetails() {
        return backUser;
    }

    @Override
    public Object getPrincipal() {
        return phoneNumber;
    }

    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        super.setAuthenticated(isAuthenticated);
    }

    @Override
    public String getName() {
        return this.phoneNumber;
    }

}
