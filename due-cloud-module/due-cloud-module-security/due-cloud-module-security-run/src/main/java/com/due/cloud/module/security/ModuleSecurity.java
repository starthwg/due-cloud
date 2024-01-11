package com.due.cloud.module.security;

import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;

/**
 * 安全模块启动类
 *
 * @author hanwengang
 */
@DueStart
public class ModuleSecurity extends Application {
    public static void main(String[] args) {
        new ModuleSecurity().run(args);
    }
}