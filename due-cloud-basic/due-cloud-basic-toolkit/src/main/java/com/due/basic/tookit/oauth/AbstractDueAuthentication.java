package com.due.basic.tookit.oauth;

import com.due.basic.tookit.oauth.user.DueBasicUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

/**
 * 项目认证对象
 *
 * @author hanwengang
 */
@Setter
@Getter
public abstract class AbstractDueAuthentication implements Authentication {


    /**
     * 用户ID
     */
    private Long memberId;


    /**
     * 认证类型
     */
    private String grantType;



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

    public AbstractDueAuthentication(String grantType) {
        this.grantType = grantType;
    }



    public AbstractDueAuthentication(String grantType, boolean authenticated, Collection<? extends GrantedAuthority> authorities) {
        this.grantType = grantType;
        this.authenticated = authenticated;
        this.authorities = authorities;
    }

    public AbstractDueAuthentication(Long memberId, String grantType, boolean authenticated, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.grantType = grantType;

        this.authenticated = authenticated;
        this.authorities = authorities;
    }

    public AbstractDueAuthentication(Long memberId, String grantType, boolean authenticated, DueBasicUser dueBasicUser, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.grantType = grantType;

        this.authenticated = authenticated;
        this.dueBasicUser = dueBasicUser;
        this.authorities = authorities;
    }

    public AbstractDueAuthentication() {
    }

    @Override
    public Object getCredentials() {
        return Optional.ofNullable(dueBasicUser).map(UserDetails::getPassword).orElse(null);
    }

    @Override
    public Object getDetails() {
        return dueBasicUser;
    }

    @Override
    public Object getPrincipal() {
        return Optional.ofNullable(dueBasicUser).map(UserDetails::getUsername).orElse(null);
    }
}
