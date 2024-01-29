package com.due.basic.tookit.start.impl;

import com.alibaba.fastjson2.JSONObject;
import com.due.basic.tookit.spi.SPIName;
import com.due.basic.tookit.spi.SPIOrder;
import com.due.basic.tookit.spi.SPIServerLoad;
import com.due.basic.tookit.start.ApplicationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 打印启动日志
 */
@SPIName(name = "log")
@SPIOrder(value = 0)
@Slf4j
public class StartLogApplicationListener implements ApplicationListener {

    private static final int LENGTH = 120;

    /**
     * 启动时间
     */
    private Long startTime;

    /**
     * 服务名称
     */
    private String moduleName;


    /**
     * 容器
     */
    private ConfigurableApplicationContext configurableApplicationContext;

    /**
     * 启动参数
     */
    private String[] ages;

    @Override
    public void startBefore(String moduleName, SpringApplicationBuilder applicationBuilder, String[] ages) {
        this.startTime = System.currentTimeMillis();
        this.ages = ages;
        this.moduleName = moduleName;
    }

    @Override
    public void startAfter(String moduleName, ConfigurableApplicationContext configurableApplicationContext, String[] ages) {
//        Long time = System.currentTimeMillis() - this.startTime;
//        StringBuilder builder = new StringBuilder();
//        builder.append("\n");
//        for (int i = 0; i < LENGTH; i++) {
//            builder.append("=");
//        }
//        builder.append("\n\n");
//        String moduleNameLog = String.format("%s service started successfully Time taken %s seconds", moduleName, time);
//        int half = LENGTH - moduleNameLog.length() / 2;
//        for (int i = 0; i < half; i++) {
//            builder.append(" ");
//        }
////        builder.append(moduleNameLog);
//        builder.append("\n");
//        builder.append("启动参数：").append(Arrays.toString(ages)).append("\n");
//        builder.append("容器：").append(configurableApplicationContext).append("\n");
//        for (int i = 0; i < half; i++) {
//            builder.append(" ").append(moduleNameLog).append(" ");
//        }
//        builder.append("\n\n");
//        for (int i = 0; i < LENGTH; i++) {
//            builder.append("=");
//        }
//        log.info(builder.toString());
        long second = TimeUnit.SECONDS.convert(System.currentTimeMillis() - this.startTime, TimeUnit.MILLISECONDS);

        String message = String.format("%s Application Start Success %s Seconds", moduleName, second);
        String separater = "";
        for (int i = 0; i < LENGTH; i++) {
            separater += "=";
        }
        int total = (LENGTH - message.length()) / 2;
        for (int i = 0; i < total; i++) {
            message = " " + message + " ";
        }
        StringBuffer sb = new StringBuffer("\n");
        sb.append(separater);
        sb.append("\n\n");
        sb.append(message);
        sb.append("\n\n");
        sb.append(separater);
        log.info(sb.toString());
    }

    public static void main(String[] args) {
        SPIServerLoad<ApplicationListener> serverLoad = SPIServerLoad.getServerLoad(ApplicationListener.class);
        List<ApplicationListener> extensionList = serverLoad.getExtensionList();
        extensionList.forEach(e -> e.startBefore("111", null, null));
        extensionList.forEach(e -> e.startAfter("111", null, null));
    }
}
