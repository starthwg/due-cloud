package com.cloud.bridge.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * 委派的认证提供者
 *
 * @author hanwengang
 */
public class DelegateDeuAuthenticationProvider implements AuthenticationProvider {


    private final List<AuthenticationProvider> dueAuthenticationProviderList = new ArrayList<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : dueAuthenticationProviderList) {
            if (!provider.supports(authentication.getClass())) {
                continue;
            }
            return provider.authenticate(authentication);
        }
        throw new InternalAuthenticationServiceException("没有找到对应的认证处理器");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return dueAuthenticationProviderList.stream().anyMatch(e -> e.supports(authentication));
    }

    public void addProvider(AuthenticationProvider provider) {
        this.dueAuthenticationProviderList.add(provider);
    }

    public void addProvider(List<AuthenticationProvider> providerList) {
        this.dueAuthenticationProviderList.addAll(providerList);
    }
}
