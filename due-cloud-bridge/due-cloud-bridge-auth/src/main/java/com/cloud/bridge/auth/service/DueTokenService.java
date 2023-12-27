package com.cloud.bridge.auth.service;

import com.cloud.bridge.auth.user.DueBasicUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 生成token的服务
 *
 * @author hanwengang
 */
public interface DueTokenService<T extends DueBasicUser> {


    /**
     * 创建token
     *
     * @param user 用户信息
     * @return string类型的token
     */
    String createToken(T user);


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
    T tokenConvertUserDetail(String token);
}
