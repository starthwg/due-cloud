package com.due.cloud.service.auth.config;

import com.cloud.bridge.auth.OpenIdNotFoundException;
import com.cloud.bridge.auth.service.DueUserDetailService;
import com.cloud.bridge.auth.user.BackUser;
import com.cloud.bridge.auth.user.MobileUser;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.enums.ChannelEnum;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import com.due.cloud.module.customer.api.domain.enums.CustomerAccountTypeEnum;
import com.due.cloud.module.customer.api.domain.request.SelectCustomerAccount;
import com.due.cloud.module.customer.api.domain.response.Customer;
import com.due.cloud.module.customer.api.domain.response.CustomerAccount;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.service.auth.service.customer.ICustomerService;
import com.due.cloud.service.auth.service.security.ISystemMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 认证是获取用户信息的service
 *
 * @author Administrator
 */
@Component
@Slf4j
public class DefaultDueUserDetailServiceImpl implements DueUserDetailService {

    @Autowired
    private ISystemMemberService systemMemberService;

    @Autowired
    private ICustomerService customerService;

    public DefaultDueUserDetailServiceImpl() {
        log.info("DefaultDueUserDetailServiceImpl 实例化");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ThreadLocalUtil.set(GlobalThreadLocalConstant.CHANNEL_ENUM, ChannelEnum.BACK);
        SystemMember systemMemberByUsername = systemMemberService.getSystemMemberByUsername(username);
        if (null == systemMemberByUsername) {
            throw new UsernameNotFoundException("用户存在！");
        }

        // 获取用户的权限
        List<SystemRole> systemRoleList = systemMemberService.getSystemRoleByMemberId(systemMemberByUsername.getDataId());
        if (null == systemRoleList) systemRoleList = new ArrayList<>();
        List<String> roles = systemRoleList.stream().map(SystemRole::getCode).distinct().collect(Collectors.toList());
        return new BackUser(systemMemberByUsername.getDataId(), systemMemberByUsername.getUsername(),
                systemMemberByUsername.getLocked().equals(1), roles, systemMemberByUsername.getPassword(), systemMemberByUsername.getMobile());
    }

    @Override
    public UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        CustomerAccount account = customerService.getCustomerAccountCondition(SelectCustomerAccount.of().setSecret(openId).setType(CustomerAccountTypeEnum.WX_OPENID.getCode()));
        if (null == account) {
            throw new OpenIdNotFoundException("OpenId对应的用户不存在！");
        }
        // 获取用户信息
        Customer customer = customerService.getCustomerByDataId(account.getCustomerId());
        if (null == customer) {
            // 创建用户信息
            throw new UsernameNotFoundException("OpenId对应的用户不存在！");
        }
        return new MobileUser(account.getDataId(), openId, customer.getNickName(), customer.getPhoneNumber(), customer.getHeadImage(), null, Optional.of(customer.getLocked()).map(e -> e.equals(0)).orElse(false));
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
