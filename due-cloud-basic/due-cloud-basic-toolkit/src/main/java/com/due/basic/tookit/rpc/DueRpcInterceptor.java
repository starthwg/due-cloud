package com.due.basic.tookit.rpc;

import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在进行rpc调用时，解析调用放和服务提供方法
 *
 * @author hanwengang
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DueRpcInterceptor {

    /**
     * 服务调用方
     */
    ServiceCodeEnum request() default ServiceCodeEnum.UNKNOWN;


    /**
     * 服务响应方
     */
    ModuleCodeEnum response() default ModuleCodeEnum.CUSTOMER;


    /**
     * 服务场景
     */
    ModuleServiceScene scene() default ModuleServiceScene.UNKNOWN;
}
