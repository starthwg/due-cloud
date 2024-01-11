package com.cloud.bridge.auth.service.impl;

import com.cloud.bridge.auth.service.DueTokenService;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.oauth.Token;
import com.due.basic.tookit.oauth.service.impl.UUIDTokenEnhance;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.due.basic.tookit.utils.LogicUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DueTokenServiceImpl implements DueTokenService {

    private static final Map<String, DueBasicUser> CACHE_TOKEN_DUE_BASIC_USER = new ConcurrentHashMap<>();

    private static final Map<Long, String> MEMBER_ID_TOKEN_MAP = new ConcurrentHashMap<>();

    private UUIDTokenEnhance uuidTokenEnhance;

    public UUIDTokenEnhance getUuidTokenEnhance() {
        return uuidTokenEnhance;
    }

    public void setUuidTokenEnhance(UUIDTokenEnhance uuidTokenEnhance) {
        this.uuidTokenEnhance = uuidTokenEnhance;
    }

    @Override
    public Token createToken(DueBasicUser user) {
        String accessToken = MEMBER_ID_TOKEN_MAP.get(user.getMemberId());
        if (LogicUtil.isAllBlank(accessToken)) {
            accessToken = Optional.ofNullable(uuidTokenEnhance).map(e -> e.enhance(user)).orElseGet(() -> UUID.randomUUID().toString());
            MEMBER_ID_TOKEN_MAP.put(user.getMemberId(), accessToken);
        }
        CACHE_TOKEN_DUE_BASIC_USER.put(accessToken, user);
        Token token = new Token();
        token.setAccessToken(accessToken);
        return token;
    }

    @Override
    public String getToken(HttpServletRequest httpServletRequest) {
        if (null == httpServletRequest) return null;
        String token = httpServletRequest.getHeader(GlobalAuthConstant.BEARER);
        if (LogicUtil.isAllBlank(token)) {
            token = httpServletRequest.getHeader(GlobalAuthConstant.BEARER.toLowerCase());
        }
        if (LogicUtil.isAllBlank(token)) return null;
        return token;
    }

    @Override
    public DueBasicUser tokenConvertUserDetail(String token) {
        if (LogicUtil.isAllBlank(token)) return null;
        return CACHE_TOKEN_DUE_BASIC_USER.get(token);
    }
}
