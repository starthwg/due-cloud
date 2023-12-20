package com.due.basic.tookit.yml;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.io.support.PropertySourceFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  导入外部的yml配置文件
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PropertySource("")
public @interface YamlPropertySource {

    /**
     *  文件名称
     */
    @AliasFor(annotation = PropertySource.class)
    String name() default "";


    /**
     *
     * 值
     */
    @AliasFor(annotation = PropertySource.class)
    String[] value();


    /**
     *  是否忽略未找到的资源
     * @return
     */
    @AliasFor(annotation = PropertySource.class)
    boolean ignoreResourceNotFound() default false;

    /**
     *  编码类型
     * @return
     */
    @AliasFor(annotation = PropertySource.class)
    String encoding() default "";


    @AliasFor(annotation = PropertySource.class)
    Class<? extends PropertySourceFactory> factory() default YamlPropertySourceFactory.class;
}
