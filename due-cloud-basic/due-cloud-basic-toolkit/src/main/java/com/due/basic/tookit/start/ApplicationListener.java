package com.due.basic.tookit.start;

import com.due.basic.tookit.spi.SPI;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *  自定义程序启动监听器
 */
@SPI("dueApplicationListener")
public interface ApplicationListener {

    /**
     *  程序启动之前
     * @param moduleName 模块名称
     * @param applicationBuilder
     * @param ages 参数
     */
    default void startBefore(String moduleName, SpringApplicationBuilder applicationBuilder, String[] ages){

    }

    /**
     *  程序启动之后
     * @param moduleName
     * @param ages
     */
    default void startAfter(String moduleName, ConfigurableApplicationContext configurableApplicationContext, String[] ages) {

    }
}
