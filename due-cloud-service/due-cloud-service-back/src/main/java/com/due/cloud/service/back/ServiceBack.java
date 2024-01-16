package com.due.cloud.service.back;

import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;
import com.due.cloud.bridge.resource.EnableDueResources;

/**
 * 后台服务启动类
 *
 * @author hanwengang
 */
@DueStart
@EnableDueResources
public class ServiceBack extends Application {
    public static void main(String[] args) {
        new ServiceBack().run(args);
    }
}
