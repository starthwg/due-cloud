package com.cloud.bridge.auth;

import org.springframework.security.core.AuthenticationException;

/**
 * openId不存在的异常信息
 */
public class OpenIdNotFoundException extends AuthenticationException {
    public OpenIdNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs a {@code UsernameNotFoundException} with the specified message and root
     * cause.
     *
     * @param msg   the detail message.
     * @param cause root cause
     */
    public OpenIdNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
