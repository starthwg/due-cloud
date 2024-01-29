package com.cloud.bridge.auth.service;

import com.cloud.bridge.auth.config.AuthBridgeProperties;
import com.dou.cloud.bridge.redis.service.RedisService;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.oauth.Token;
import com.due.basic.tookit.oauth.service.DueTokenService;
import com.due.basic.tookit.oauth.service.TokenEnhance;
import com.due.basic.tookit.oauth.service.impl.UUIDTokenEnhance;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.due.basic.tookit.utils.LogicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
public class DueRedisServiceImpl implements DueTokenService {


    private static final String TOKEN_PREFIX = "auth:token";

    private static final String MEMBER_TOKEN = "auth:member";

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;


    /**
     * redisService
     */
    @Autowired
    private RedisService redisService;

    /**
     * token增强器
     */
    private final TokenEnhance tokenEnhance = new UUIDTokenEnhance();

    /**
     * 配置信息
     */
    @Autowired
    private AuthBridgeProperties authBridgeProperties;


    @Override
    public Token createToken(DueBasicUser user) {
        if (null == user) {
            throw new AuthenticationServiceException("认证异常！");
        }
        Token token = new Token();
        String keyMember = String.format("%s:%s:%s", applicationName, MEMBER_TOKEN, String.valueOf(user.getMemberId()));
        if (redisService.hasKey(keyMember)) {
            // 设置旧的1分钟后过期
            redisService.expire(keyMember, 60);
            Object object = redisService.get(keyMember);
            String tokenKey = String.format("%s:%s:%s", applicationName, TOKEN_PREFIX, object.toString());
            redisService.expire(tokenKey, 60);
        }
        // 没有生成新的token
        //
        String enhance = tokenEnhance.enhance(user);
        // 获取token的有效期
        Long expiration = this.authBridgeProperties.getExpiration();

        // 设置token的有效期
        token.setExpiration(System.currentTimeMillis() + expiration);
        // 设置token
        String key = String.format("%s:%s:%s", applicationName, TOKEN_PREFIX, enhance);
        redisService.set(key, user, expiration);

        // 设置memberId
        redisService.set(keyMember, enhance, expiration);

        token.setAccessToken(enhance);


        return token;
    }


    @Override
    public String getToken(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest).filter(e -> LogicUtil.isAllNotBlank(e.getHeader(GlobalAuthConstant.Authorization))).map(e -> {
            String header = e.getHeader(GlobalAuthConstant.Authorization);
            return header.replace(GlobalAuthConstant.BEARER, "");
        }).orElseGet(() -> null);
    }

    @Override
    public DueBasicUser tokenConvertUserDetail(String token) {
        Object object = redisService.get(this.getKey(token));
        return (DueBasicUser) object;
    }

    @Override
    public boolean removeToken(String token) {
        String key = String.format("%s:%s:%s", this.applicationName, TOKEN_PREFIX, token);
        log.info("key：{}", key);
        Object object = this.redisService.get(key);
        if (null == object) return true;
        if (object instanceof DueBasicUser) {
            DueBasicUser user = (DueBasicUser) object;
            Long memberId = user.getMemberId();
            // 删除memberId
            String memberKey = String.format("%s:%s:%s", this.applicationName, MEMBER_TOKEN, memberId);
            this.redisService.delete(memberKey, key);
            return true;
        }
        return true;
    }


    private String getKey(String token) {
        return String.format("%s:%s:%s", this.applicationName, TOKEN_PREFIX, token);
    }
}
