package com.due.cloud.bridge.resource;

import com.due.cloud.bridge.resource.config.DueBridgeResourcesConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *  开启资源服务器
 * @author hanwengang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {DueBridgeResourcesConfig.class})
public @interface EnableDueResources {
}
