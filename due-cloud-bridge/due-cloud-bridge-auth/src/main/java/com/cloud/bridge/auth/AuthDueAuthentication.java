package com.cloud.bridge.auth;

import com.cloud.bridge.auth.grant.TokenRequest;
import com.due.basic.tookit.oauth.AbstractDueAuthentication;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 项目认证对象
 *
 * @author hanwengang
 */
@Setter
@Getter
public abstract class AuthDueAuthentication extends AbstractDueAuthentication {


    /**
     * 获取token的请求
     */
    private TokenRequest tokenRequest;


    public AuthDueAuthentication(String grantType) {
        super(grantType);
    }

    public AuthDueAuthentication(String grantType, TokenRequest tokenRequest) {
        super(grantType);
        this.tokenRequest = tokenRequest;
    }

    public AuthDueAuthentication(String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities) {
        super(grantType, authenticated, authorities);
        this.tokenRequest = tokenRequest;
    }

    public AuthDueAuthentication(Long memberId, String grantType, TokenRequest tokenRequest, boolean authenticated, Collection<? extends GrantedAuthority> authorities) {
        super(memberId, grantType, authenticated, authorities);
        this.tokenRequest = tokenRequest;
    }

    public AuthDueAuthentication(Long memberId, String grantType, TokenRequest tokenRequest, boolean authenticated, DueBasicUser dueBasicUser, Collection<? extends GrantedAuthority> authorities) {
        super(memberId, grantType, authenticated, dueBasicUser, authorities);
        this.tokenRequest = tokenRequest;
    }

    public AuthDueAuthentication() {
    }

}
