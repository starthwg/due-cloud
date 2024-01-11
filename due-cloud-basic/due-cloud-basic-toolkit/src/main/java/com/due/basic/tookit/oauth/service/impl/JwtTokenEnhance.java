package com.due.basic.tookit.oauth.service.impl;

import com.due.basic.tookit.oauth.service.TokenEnhance;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.due.basic.tookit.utils.JwtUtil;
import com.due.basic.tookit.utils.LogicUtil;
import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * jwt token增强器
 *
 * @author Administrator
 */
public class JwtTokenEnhance implements TokenEnhance {

    /**
     * 秘钥
     */
    private final String secretKey;


    /**
     * 过期时间
     */
    private final Long expireTime;

    public JwtTokenEnhance(String secretKey, Long expireTime) {
        this.secretKey = secretKey;
        this.expireTime = expireTime;
    }


    @Override
    public String enhance(DueBasicUser user) {
        if (null == user) return null;
        String encode = JwtUtil.encode(secretKey, System.currentTimeMillis() + expireTime, user);
        if (LogicUtil.isAllBlank(encode)) {
            throw new AuthenticationServiceException("生成JWT失败");
        }
        return encode;
    }
}
