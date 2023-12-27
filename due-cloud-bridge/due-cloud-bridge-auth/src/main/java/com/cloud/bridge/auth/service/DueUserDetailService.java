package com.cloud.bridge.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 */
public interface DueUserDetailService extends UserDetailsService {

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("用户名密码错误");
    }

    /**
     * 通过openId 获取用户
     *
     * @param openId openId
     * @return UserDetails
     * @throws UsernameNotFoundException openId错误
     */
    default UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("openId错误");
    }


    /**
     * 通过手机验证码获取用户详情
     *
     * @param phoneNumber 手机号码
     * @param code        验证码
     * @return userDetail
     * @throws UsernameNotFoundException 了许
     */
    default UserDetails loadUserByCode(String phoneNumber, String code) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("验证码错误！");
    }


    /**
     *  移动端 账户密码登录
     * @param username 用户名称
     * @return UserDetails 信息
     * @throws UsernameNotFoundException 用户密码错误
     */
    default UserDetails loadUserByPassword(String username) throws UsernameNotFoundException{
        throw new UsernameNotFoundException("用户名密码错误");
    }
}
