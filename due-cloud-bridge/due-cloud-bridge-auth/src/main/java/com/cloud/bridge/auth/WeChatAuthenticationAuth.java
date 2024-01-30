package com.cloud.bridge.auth;

import com.cloud.bridge.auth.grant.TokenRequest;
import com.cloud.bridge.auth.user.MobileUser;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WeChatAuthenticationAuth extends AuthDueAuthentication {

    /**
     * 微信小程序OpenId
     */
    private String openId;


    @Override
    public String getName() {
        return openId;
    }

    public WeChatAuthenticationAuth(String openId) {
        this.openId = openId;
    }

    public WeChatAuthenticationAuth() {
    }

    public WeChatAuthenticationAuth(String grantType, TokenRequest tokenRequest, String openId) {
        super(grantType, tokenRequest);
        this.openId = openId;
    }

    public WeChatAuthenticationAuth(String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities, String openId) {
        super(grantType, tokenRequest, authenticated, authorities);
        this.openId = openId;
    }

    public WeChatAuthenticationAuth(Long memberId, String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities, String openId) {
        super(memberId, grantType, tokenRequest, authenticated, authorities);
        this.openId = openId;
    }

    public WeChatAuthenticationAuth(Long memberId, String grantType, TokenRequest tokenRequest, boolean authenticated, DueBasicUser dueBasicUser, Collection<? extends GrantedAuthority> authorities, String openId) {
        super(memberId, grantType, tokenRequest, authenticated, dueBasicUser, authorities);
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public Object getCredentials() {
        return openId;
    }

    @Override
    public Object getPrincipal() {
        return openId;
    }

    public WeChatAuthenticationAuth(Long memberId, String grantType, TokenRequest tokenRequest, boolean authenticated, DueBasicUser dueBasicUser, Collection<? extends GrantedAuthority> authorities, String openId, MobileUser mobileUser) {
        super(memberId, grantType, tokenRequest, authenticated, dueBasicUser, authorities);
        this.openId = openId;
    }
}
