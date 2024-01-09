package com.cloud.bridge.auth.config;

import com.cloud.bridge.auth.convert.authentication.RequestTokenAuthenticationConvert;
import com.cloud.bridge.auth.convert.authentication.RequestTokenBackCodeConvert;
import com.cloud.bridge.auth.convert.authentication.RequestTokenBackPasswordConvert;
import com.cloud.bridge.auth.filter.DueAuthenticationProcessFilter;
import com.cloud.bridge.auth.handler.CustomizeAuthenticationFailed;
import com.cloud.bridge.auth.handler.CustomizeAuthenticationSuccessHandler;
import com.cloud.bridge.auth.provider.BackPasswordAuthenticationProvider;
import com.cloud.bridge.auth.provider.DelegateDeuAuthenticationProvider;
import com.cloud.bridge.auth.provider.DueAuthenticationProvider;
import com.cloud.bridge.auth.service.DueUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
@ComponentScan("com.cloud.bridge.auth")
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
                .addFilterBefore(this.authenticationProcessFilter(), UsernamePasswordAuthenticationFilter.class).build();
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

        dueAuthenticationProcessFilter.setAuthenticationSuccessHandler(new CustomizeAuthenticationSuccessHandler());
        dueAuthenticationProcessFilter.setAuthenticationFailureHandler(new CustomizeAuthenticationFailed());


        return dueAuthenticationProcessFilter;
    }
}
