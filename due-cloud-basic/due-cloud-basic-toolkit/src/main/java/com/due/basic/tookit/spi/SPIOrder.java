package com.due.basic.tookit.spi;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * spi的执行顺序
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPIOrder {

    /**
     * 加载的顺序
     *
     * @return
     */
    int value() default Integer.MAX_VALUE;
}
