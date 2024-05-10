package com.due.cloud.bridge.resource.config;

import com.alibaba.fastjson2.JSONObject;
import com.due.cloud.bridge.resource.filter.DueTokenConvertUserDetailFilter;
import com.due.cloud.bridge.resource.filter.RemoteTokenConvertFilter;
import com.due.cloud.bridge.resource.handler.AuthorizeService;
import com.due.cloud.bridge.resource.handler.CustomeizeAuthenticationEntryPoint;
import com.due.cloud.bridge.resource.handler.CustomizeAccessDeniedHandler;
import com.due.cloud.bridge.resource.handler.DefaultAuthorizeService;
import com.due.cloud.bridge.resource.parser.DueWebDueSecurityExpressionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@EnableWebSecurity
@Configuration
@EnableConfigurationProperties(value = {DueResourcesProperties.class})
@Import(value = {RemoteTokenConvertFilter.class})
public class DueBridgeResourcesConfig implements ApplicationContextAware {

    @Autowired
    private DueResourcesProperties dueResourcesProperties;

    @Autowired
    private DueTokenConvertUserDetailFilter dueTokenConvertUserDetailFilter;

    private ApplicationContext applicationContext;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(customize -> customize.accessDeniedHandler(new CustomizeAccessDeniedHandler()).authenticationEntryPoint(new CustomeizeAuthenticationEntryPoint()))
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                // 直接放行的请求
                .authorizeRequests(customizer -> customizer.antMatchers(this.antMatchers()).permitAll())
                .authorizeRequests(customizer -> customizer.antMatchers("/due/mobile/**").authenticated())
                .authorizeRequests(customizer -> customizer.expressionHandler(this.dueWebDueSecurityExpressionHandler()).anyRequest().access("@authorizeService.access(request,authentication )"))

                .addFilterBefore(dueTokenConvertUserDetailFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * 在没有认证处理的时候 默认加载的
     */
    @ConditionalOnMissingBean
    @Bean
    public AuthorizeService authorizeService() {
        return new DefaultAuthorizeService();
    }


    public String[] antMatchers() {
        DueResourcesProperties.Auth auth = this.dueResourcesProperties.getAuth();
        if (null == auth) return new String[0];
        Set<String> urls = auth.getIgnoreUrls();
        if (null == urls) {
            urls = new HashSet<>();
        }
        log.info("不需要认证可以访问的url:{}", JSONObject.toJSONString(urls));
        return urls.toArray(new String[0]);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public DueWebDueSecurityExpressionHandler dueWebDueSecurityExpressionHandler() {
        DueWebDueSecurityExpressionHandler dueWebDueSecurityExpressionHandler = new DueWebDueSecurityExpressionHandler();
        dueWebDueSecurityExpressionHandler.setApplicationContext(applicationContext);
        return dueWebDueSecurityExpressionHandler;
    }
}
