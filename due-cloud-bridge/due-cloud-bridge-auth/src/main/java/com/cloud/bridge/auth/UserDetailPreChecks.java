package com.cloud.bridge.auth;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailPreChecks implements DuePreAuthenticationChecks {
    @Override
    public void check(UserDetails userDetails) {
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("账户被锁定！");
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("账户已过期！");
        }
        if (!userDetails.isEnabled()) {
            throw new DisabledException("账户被锁定！");
        }
    }
}
