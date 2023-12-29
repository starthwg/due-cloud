package com.due.cloud.service.auth.config;

import com.cloud.bridge.auth.service.DueUserDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *  认证是获取用户信息的service
 * @author Administrator
 */
@Component
public class DefaultDueUserDetailServiceImpl implements DueUserDetailService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return DueUserDetailService.super.loadUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        return DueUserDetailService.super.loadUserByOpenId(openId);
    }

    @Override
    public UserDetails loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        return DueUserDetailService.super.loadUserByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDetails loadUserByPassword(String username) throws UsernameNotFoundException {
        return DueUserDetailService.super.loadUserByPassword(username);
    }
}
