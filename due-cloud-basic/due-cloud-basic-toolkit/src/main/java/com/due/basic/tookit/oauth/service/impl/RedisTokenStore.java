package com.due.basic.tookit.oauth.service.impl;

import com.due.basic.tookit.oauth.service.TokenStore;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import org.springframework.security.core.Authentication;


/**
 *
 */
public class RedisTokenStore implements TokenStore {
    @Override
    public Authentication getAuthentication(String token) {
        return null;
    }

    @Override
    public String getToken(DueBasicUser user) {
        return null;
    }
}
