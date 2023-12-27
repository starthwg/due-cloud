package com.due.cloud.service.mobile;

import com.due.basic.setting.logger.EnableDueLogger;
import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;

@DueStart
public class ServiceMobile extends Application {

    public static void main(String[] args) {
        new ServiceMobile().run(args);
    }
}
