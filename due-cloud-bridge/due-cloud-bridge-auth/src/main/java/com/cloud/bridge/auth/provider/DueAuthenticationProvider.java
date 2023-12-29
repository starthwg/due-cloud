package com.cloud.bridge.auth.provider;

import com.cloud.bridge.auth.DueAuthentication;
import com.cloud.bridge.auth.UserDetailPreChecks;
import com.cloud.bridge.auth.convert.user.GrantTypeUsernameCovert;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目认证提供者
 *
 * @author hanwengang
 */
@Slf4j
public abstract class DueAuthenticationProvider<T extends DueAuthentication> implements AuthenticationProvider, ApplicationContextAware, InitializingBean {


    /**
     * 是否隐藏找不到用户信息的异常
     */
    protected boolean hideUserNotFoundExceptions = false;

    /**
     * 将用户名转换成user对象
     */
    private List<GrantTypeUsernameCovert> grantTypeUsernameCovertList;

    /**
     * bean工厂
     */
    protected ApplicationContext applicationContext;

    /**
     * 验证用户账户信息
     */
    private final UserDetailsChecker proUserDetailChecker = new UserDetailPreChecks();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = this.getName(authentication);
        UserDetails user = null;
        try {
            user = this.retrieveUser(username, authentication);
        } catch (UsernameNotFoundException exception) {
            log.error("User name that does not exist {}", username);
            if (!this.hideUserNotFoundExceptions) {
                throw exception;
            }
            throw new BadCredentialsException("密码错误！");
        }
        LogicAssert.isNull(user, ErrorEnum.DATA_HANDLE_ERROR);

        // 验证账户信息是否被锁定
        this.proUserDetailChecker.check(user);

        /**
         *  验证账户密钥信息
         */
        this.checkCredentials(user, authentication);

        /**
         *  返回响应对象
         */
        return createSuccessAuthentication(user, authentication);
    }

    public String getName(Authentication authentication) {
        return authentication.getPrincipal() == null ? "not_provider" : authentication.getName();
    }


    public UserDetails retrieveUser(String username, Authentication authentication) {
        String grantType;
        if (authentication instanceof DueAuthentication) {
            DueAuthentication dueAuthentication = (DueAuthentication) authentication;
            grantType = dueAuthentication.getGrantType();
        } else {
            grantType = null;
        }
        return grantTypeUsernameCovertList.stream().filter(e -> e.support(grantType)).findFirst()
                .map(e -> e.convert(username)).orElse(null);
    }

    /**
     * 认证成功转化认证对象
     *
     * @param userDetails    用户信息
     * @param authentication 请求的认证对象
     * @return 返回认证对象
     */
    protected abstract T createSuccessAuthentication(UserDetails userDetails, Authentication authentication);


    /**
     * 验证凭证是否正确
     *
     * @param userDetails    用户信息
     * @param authentication 用户认证信息
     */
    protected abstract void checkCredentials(UserDetails userDetails, Authentication authentication);

    @Override
    public void afterPropertiesSet() throws Exception {
        this.grantTypeUsernameCovertList = new ArrayList<>(applicationContext.getBeansOfType(GrantTypeUsernameCovert.class)
                .values());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
