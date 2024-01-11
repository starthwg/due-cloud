package com.cloud.bridge.auth.service;

import com.due.basic.tookit.oauth.Token;
import com.due.basic.tookit.oauth.user.DueBasicUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 生成token的服务
 *
 * @author hanwengang
 */
public interface DueTokenService {


    /**
     * 创建token
     *
     * @param user 用户信息
     * @return string类型的token
     */
    Token createToken(DueBasicUser user);


    /**
     * 获取token
     *
     * @param httpServletRequest httpServletRequest 请求对象
     * @return string Token
     */
    String getToken(HttpServletRequest httpServletRequest);


    /**
     * token转换成 用户信息
     *
     * @param token string类型的token
     * @return DueBasicUser 类型
     */
    DueBasicUser tokenConvertUserDetail(String token);
}
