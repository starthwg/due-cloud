package com.cloud.bridge.auth.provider;

import com.cloud.bridge.auth.BackPasswordAuthentication;
import com.cloud.bridge.auth.user.BackUser;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.due.basic.tookit.utils.LogicUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * 后台用户账户密码认证
 *
 * @author Administrator
 */
public class BackPasswordAuthenticationProvider extends DueAuthenticationProvider<BackPasswordAuthentication> {


    private final PasswordEncoder passwordEncoder;


    @Override
    protected BackPasswordAuthentication createSuccessAuthentication(UserDetails userDetails, Authentication authentication) {
        DueBasicUser dueBasicUser = (DueBasicUser) userDetails;
        BackPasswordAuthentication backPasswordAuthentication = (BackPasswordAuthentication) authentication;
        return new BackPasswordAuthentication(backPasswordAuthentication.getGrantType(),
                backPasswordAuthentication.getTokenRequest(),
                true,
                userDetails.getAuthorities(), dueBasicUser.getMemberId(), dueBasicUser.getUsername(), null, (BackUser) dueBasicUser);
    }

    @Override
    protected void checkCredentials(UserDetails userDetails, Authentication authentication) {
        String requestPassword = Optional.ofNullable(authentication).flatMap(e -> Optional.ofNullable(e.getCredentials())).map(Object::toString).orElse(null);
        if (LogicUtil.isAllBlank(requestPassword)) {
            throw new BadCredentialsException("密码错误！");
        }
        String password = userDetails.getPassword();
        if (!passwordEncoder.matches(requestPassword, password)) {
            throw new BadCredentialsException("密码错误！");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BackPasswordAuthentication.class.isAssignableFrom(authentication);
    }

    public BackPasswordAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
