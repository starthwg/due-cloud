package com.due.cloud.service.auth;

import com.cloud.bridge.auth.EnableDueAuthService;
import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

/**
 * 认证服务启动类
 *
 * @author hanwengang
 */
@DueStart(exclude = {SecurityFilterAutoConfiguration.class})
@EnableDueAuthService
public class ServiceAuth extends Application {
    public static void main(String[] args) {
        new ServiceAuth().run(args);
    }
}
