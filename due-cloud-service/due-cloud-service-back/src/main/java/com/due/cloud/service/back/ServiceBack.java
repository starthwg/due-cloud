package com.due.cloud.service.back;

import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;
import com.due.cloud.bridge.resource.EnableDueResources;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * 后台服务启动类
 *
 * @author hanwengang
 */
@DueStart(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableDueResources
public class ServiceBack extends Application {
    public static void main(String[] args) {
        new ServiceBack().run(args);
    }
}
