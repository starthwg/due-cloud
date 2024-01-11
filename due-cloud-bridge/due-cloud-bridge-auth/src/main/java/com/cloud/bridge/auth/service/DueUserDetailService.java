package com.cloud.bridge.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * userDetailsService
 *
 * @author hanwengang
 */
public interface DueUserDetailService extends UserDetailsService {

    /**
     * 后台用户通过账户密码认证
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails 用户信息
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("用户信息不存在");
    }

    /**
     * 通过openId 获取用户
     *
     * @param openId openId
     * @return UserDetails
     * @throws UsernameNotFoundException openId错误
     */
    default UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("openId不存在");
    }


    /**
     * 通过手机验证码获取用户详情
     *
     * @param phoneNumber 手机号码
     * @return userDetail
     * @throws UsernameNotFoundException 了许
     */
    default UserDetails loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("手机号码不存在！");
    }


    /**
     * 移动端 账户密码登录
     *
     * @param username 用户名称
     * @return UserDetails 信息
     * @throws UsernameNotFoundException 用户密码错误
     */
    default UserDetails loadUserByPassword(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("用户不存在");
    }
}
