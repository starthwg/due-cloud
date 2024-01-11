package com.cloud.bridge.auth;

import com.cloud.bridge.auth.grant.TokenRequest;
import com.cloud.bridge.auth.user.BackUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 后台 密码模式
 *
 * @author hanwengang
 */
public class BackPasswordAuthentication extends DueAuthentication {

    private String username;

    private String password;


    public BackPasswordAuthentication() {
    }

    public BackPasswordAuthentication(String grantType, TokenRequest tokenRequest, String username, String password) {
        super(grantType, tokenRequest);
        this.username = username;
        this.password = password;
    }

    public BackPasswordAuthentication(String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities, Long dataId, String username, String password, BackUser backUser) {
        super(dataId, grantType, tokenRequest, authenticated,backUser, authorities);
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public String getName() {
        return this.username;
    }
}