package com.due.basic.tookit.oauth.service;

import com.due.basic.tookit.oauth.user.DueBasicUser;
import org.springframework.security.core.Authentication;

/**
 * 回去token的商店
 *
 * @author hanwengang
 */
public interface TokenStore {

    /**
     * 通过token获取认证对象
     *
     * @param token 请求的token
     * @return Authentication 类型
     */
    Authentication getAuthentication(String token);


    /**
     * 通过用户信息获取token
     *
     * @param user 用户信息
     * @return token信息
     */
    String getToken(DueBasicUser user);
}
