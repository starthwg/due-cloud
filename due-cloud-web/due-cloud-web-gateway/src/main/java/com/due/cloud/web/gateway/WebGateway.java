package com.due.cloud.web.gateway;

import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;

@DueStart
public class WebGateway extends Application {
    public static void main(String[] args) {
        new WebGateway().run(args);
    }
}


