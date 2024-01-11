package com.due.basic.tookit.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支持SPI机制的注解
 *
 * @author hanwengang
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPI {

    /**
     * 扩展名称
     */
    String value() default "";

    /**
     * 是否是单例
     */
    SPIScope scope() default SPIScope.Singleton;
}
