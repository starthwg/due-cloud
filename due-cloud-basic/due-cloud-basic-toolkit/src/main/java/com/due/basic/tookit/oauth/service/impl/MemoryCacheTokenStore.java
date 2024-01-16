package com.due.basic.tookit.oauth.service.impl;

import com.due.basic.tookit.oauth.service.TokenStore;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import org.springframework.security.core.Authentication;

/**
 * 内存缓存token信息
 * @author hanwengang
 */
public class MemoryCacheTokenStore implements TokenStore {


    @Override
    public Authentication getAuthentication(String token) {
        return null;
    }

    @Override
    public String getToken(DueBasicUser user) {
        return null;
    }
}
