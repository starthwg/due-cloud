package com.due.cloud.module.customer;

import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;
import com.due.basic.tookit.enums.ModuleServiceNameEnum;


/**
 *  customer的启动引导类
 *
 */
@DueStart
public class ModuleCustomer extends Application {
    public static void main(String[] args) {
        new ModuleCustomer().run(args, ModuleServiceNameEnum.ModuleCustomer.name());
    }
}
