package com.due.cloud.service.auth;

import com.cloud.bridge.auth.EnableDueAuthService;
import com.cloud.bridge.auth.provider.BackPasswordAuthenticationProvider;
import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 认证服务启动类
 *
 * @author hanwengang
 */
@DueStart(exclude = {SecurityFilterAutoConfiguration.class})
@EnableDueAuthService
@Slf4j
public class ServiceAuth extends Application {
    public static void main(String[] args) {
        new ServiceAuth().run(args);
    }
}
