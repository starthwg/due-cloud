package com.cloud.bridge.auth;

import com.cloud.bridge.auth.grant.TokenRequest;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 项目认证对象
 *
 * @author hanwengang
 */
@Setter
@Getter
public abstract class DueAuthentication implements Authentication {


    /**
     * 用户ID
     */
    private Long memberId;


    /**
     * 认证类型
     */
    private String grantType;

    /**
     * 获取token的请求
     */
    private TokenRequest tokenRequest;


    /**
     * 是否已经认证
     */
    private boolean authenticated;


    /**
     * 用户信息
     */
    private DueBasicUser dueBasicUser;

    /**
     * 权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    public DueAuthentication(String grantType) {
        this.grantType = grantType;
    }

    public DueAuthentication(String grantType, TokenRequest tokenRequest) {
        this.grantType = grantType;
        this.tokenRequest = tokenRequest;
    }

    public DueAuthentication(String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities) {
        this.grantType = grantType;
        this.tokenRequest = tokenRequest;
        this.authenticated = authenticated;
        this.authorities = authorities;
    }

    public DueAuthentication(Long memberId, String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.grantType = grantType;
        this.tokenRequest = tokenRequest;
        this.authenticated = authenticated;
        this.authorities = authorities;
    }

    public DueAuthentication(Long memberId, String grantType, TokenRequest tokenRequest, boolean authenticated, DueBasicUser dueBasicUser, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.grantType = grantType;
        this.tokenRequest = tokenRequest;
        this.authenticated = authenticated;
        this.dueBasicUser = dueBasicUser;
        this.authorities = authorities;
    }

    public DueAuthentication() {
    }

}
