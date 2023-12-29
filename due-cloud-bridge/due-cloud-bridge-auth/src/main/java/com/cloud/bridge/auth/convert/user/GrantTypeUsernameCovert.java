package com.cloud.bridge.auth.convert.user;

import com.cloud.bridge.auth.convert.RequestTokenConvert;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 通过grantType转化成userDetails
 *
 * @author hanwengang
 */
public interface GrantTypeUsernameCovert extends RequestTokenConvert {

    /**
     * 转换成userDetail
     *
     * @param grantType 认证类型
     * @param username  用户名称
     * @return UserDetails
     */
    UserDetails convert(String username);
}
