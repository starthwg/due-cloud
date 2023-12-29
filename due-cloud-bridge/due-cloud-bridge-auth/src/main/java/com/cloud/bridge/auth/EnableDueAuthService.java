package com.cloud.bridge.auth;

import com.cloud.bridge.auth.config.AuthBridgeConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 开启认证服务
 *
 * @author hanwengang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {AuthBridgeConfig.class})
public @interface EnableDueAuthService {
}
