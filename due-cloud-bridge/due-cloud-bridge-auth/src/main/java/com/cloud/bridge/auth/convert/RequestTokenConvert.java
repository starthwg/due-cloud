package com.cloud.bridge.auth.convert;

import com.cloud.bridge.auth.user.DueBasicUser;

import javax.servlet.http.HttpServletRequest;

/**
 *  token转化成用户信息
 * @author hanwengang
 */
public interface RequestTokenConvert<T extends DueBasicUser> {

    /**
     *  讲请求token的token转化成 用户信息
     * @param request 请求对象
     * @return 返回用户信息
     */
    T convert(HttpServletRequest request);


    /**
     *  是否支持转化
     * @param code 编码
     * @return ture 支持 false 不支持
     */
    boolean support(int code);
}
