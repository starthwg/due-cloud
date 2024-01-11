package com.due.cloud.service.auth.config;

import com.cloud.bridge.auth.service.DueUserDetailService;
import com.cloud.bridge.auth.user.BackUser;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.enums.ChannelEnum;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.service.auth.service.ISystemMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 认证是获取用户信息的service
 *
 * @author Administrator
 */
@Component
public class DefaultDueUserDetailServiceImpl implements DueUserDetailService {

    @Autowired
    private ISystemMemberService systemMemberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ThreadLocalUtil.set(GlobalThreadLocalConstant.CHANNEL_ENUM, ChannelEnum.BACK);
        SystemMember systemMemberByUsername = systemMemberService.getSystemMemberByUsername(username);
        if (null == systemMemberByUsername) {
            throw new UsernameNotFoundException("用户存在！");
        }
        return new BackUser(systemMemberByUsername.getDataId(), systemMemberByUsername.getUsername(),
                systemMemberByUsername.getLocked().equals(1), Collections.emptyList(), systemMemberByUsername.getPassword(), systemMemberByUsername.getMobile());
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
