package com.due.basic.setting.logger;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j
public class DueLoggerSelector implements ImportSelector {


    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        log.info("数据源信息：{}", JSONObject.toJSONString(importingClassMetadata));
        String loggerClassPath = "com.due.basic.setting.logger.LoggingAspect";
        log.info("准备导入日志切面！");
        return new String[]{loggerClassPath};
    }

}
