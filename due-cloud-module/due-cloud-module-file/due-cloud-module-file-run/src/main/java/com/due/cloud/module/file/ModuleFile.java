package com.due.cloud.module.file;

import com.due.basic.setting.start.Application;
import com.due.basic.setting.start.DueStart;
import com.due.basic.tookit.enums.ModuleServiceNameEnum;

@DueStart
public class ModuleFile extends Application {

    public static void main(String[] args) {
        new ModuleFile().run(args, ModuleServiceNameEnum.ModuleFile.name());
    }
}
