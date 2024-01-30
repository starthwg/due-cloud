package com.cloud.bridge.auth.provider;

import com.cloud.bridge.auth.WeChatAuthenticationAuth;
import com.cloud.bridge.auth.user.MobileUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class MobileOpenIdAuthenticationProvider extends DueAuthenticationProvider {
    @Override
    protected Authentication createSuccessAuthentication(UserDetails userDetails, Authentication authentication) {
        MobileUser basicUser = (MobileUser) userDetails;
        WeChatAuthenticationAuth authenticationAuth = (WeChatAuthenticationAuth) authentication;
        WeChatAuthenticationAuth auth = new WeChatAuthenticationAuth(basicUser.getMemberId(), authenticationAuth.getGrantType(), authenticationAuth.getTokenRequest(), true,  basicUser, Collections.emptyList(), basicUser.getOpenId() );
        return auth;
    }

    @Override
    protected void checkCredentials(UserDetails userDetails, Authentication authentication) {

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WeChatAuthenticationAuth.class.isAssignableFrom(authentication);
    }
}
