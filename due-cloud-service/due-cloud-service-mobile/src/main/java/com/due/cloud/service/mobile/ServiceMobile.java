package com.due.cloud.service.mobile;

import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;
import com.due.cloud.bridge.resource.EnableDueResources;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@EnableDueResources
@DueStart(exclude = UserDetailsServiceAutoConfiguration.class)
public class ServiceMobile extends Application {

    public static void main(String[] args) {
        new ServiceMobile().run(args);
    }
}
