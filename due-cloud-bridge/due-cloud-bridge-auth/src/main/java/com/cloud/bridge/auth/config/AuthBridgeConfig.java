package com.cloud.bridge.auth.config;

import com.cloud.bridge.auth.convert.authentication.RequestTokenAuthenticationConvert;
import com.cloud.bridge.auth.filter.DueAuthenticationProcessFilter;
import com.cloud.bridge.auth.filter.DueLogoutProcessFilter;
import com.cloud.bridge.auth.filter.TokenConvertUserDetailFilter;
import com.cloud.bridge.auth.handler.CustomizeAuthenticationFailed;
import com.cloud.bridge.auth.handler.CustomizeAuthenticationSuccessHandler;
import com.cloud.bridge.auth.provider.DelegateDeuAuthenticationProvider;
import com.cloud.bridge.auth.service.DueRedisServiceImpl;
import com.due.basic.tookit.oauth.service.DueTokenService;
import com.due.basic.tookit.oauth.service.impl.DueTokenServiceImpl;
import com.due.basic.tookit.oauth.service.impl.UUIDTokenEnhance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@EnableWebSecurity
@Configuration
@ComponentScan("com.cloud.bridge.auth")
@EnableConfigurationProperties(value = {AuthBridgeProperties.class})
public class AuthBridgeConfig {

    @Autowired
    private List<RequestTokenAuthenticationConvert> requestTokenAuthenticationConvertList;

    @Autowired
    private List<AuthenticationProvider> authenticationProviderList;

    @Order(1)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customer -> customer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(customize -> customize.cacheControl().disable())
                .authorizeRequests().anyRequest().permitAll().and()
//                .logout(customizer -> customizer.logoutSuccessHandler().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", HttpMethod.POST.name())))
                .addFilterBefore(this.authenticationProcessFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(this.dueLogoutProcessFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(this.tokenConvertUserDetailFilter(), DueAuthenticationProcessFilter.class).build();
    }

//    @ConditionalOnMissingBean(value = DueTokenService.class)
//    @Bean
//    public DueTokenService dueTokenService() {
//        UUIDTokenEnhance uuidTokenEnhance = new UUIDTokenEnhance();
//        DueTokenServiceImpl dueTokenService = new DueTokenServiceImpl();
//        dueTokenService.setUuidTokenEnhance(uuidTokenEnhance);
//        return dueTokenService;
//    }


    @Bean
    public DueTokenService redisTokenService() {
        return new DueRedisServiceImpl();
    }

    /**
     * 注入认证管理器
     */
    private AuthenticationManager authenticationManager() {
        DelegateDeuAuthenticationProvider delegateDeuAuthenticationProvider = new DelegateDeuAuthenticationProvider();
        ProviderManager providerManager = new ProviderManager(delegateDeuAuthenticationProvider);
        delegateDeuAuthenticationProvider.addProvider(authenticationProviderList);
        try {
            return providerManager;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DueAuthenticationProcessFilter authenticationProcessFilter() {

        DueAuthenticationProcessFilter dueAuthenticationProcessFilter = new DueAuthenticationProcessFilter(this.requestTokenAuthenticationConvertList);
        dueAuthenticationProcessFilter.setAuthenticationManager(this.authenticationManager());
        CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler = new CustomizeAuthenticationSuccessHandler(this.redisTokenService());
        dueAuthenticationProcessFilter.setAuthenticationSuccessHandler(customizeAuthenticationSuccessHandler);
        dueAuthenticationProcessFilter.setAuthenticationFailureHandler(new CustomizeAuthenticationFailed());
        return dueAuthenticationProcessFilter;
    }


    public DueLogoutProcessFilter dueLogoutProcessFilter() {
        return new DueLogoutProcessFilter(this.redisTokenService(), (String) null);
    }

    public TokenConvertUserDetailFilter tokenConvertUserDetailFilter() {
        TokenConvertUserDetailFilter filter = new TokenConvertUserDetailFilter(this.redisTokenService());
        filter.setRequestMatcher(new AntPathRequestMatcher("/token/convert", HttpMethod.POST.name()));
        return filter;
    }

}
