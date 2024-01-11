package com.due.basic.tookit.spi;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * spi的扩展点的名称
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPIName {

    /**
     * 扩展点的名称
     *
     * @return
     */
    String name() default "";
}
