package com.due.bridge.tomcat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * tomcat的基础配置信息
 *
 * @author hwg
 */
@Data
@ConfigurationProperties(prefix = "due.tomcat")
public class BridgeTomcatProperties {

    /**
     * 响应的忽略包装的url
     */
    private ResponseIgnoreProperties responseIgnoreProperties = new ResponseIgnoreProperties();


    /**
     *  异步线程池的配置
     */
    private ExecutorProperties executorProperties = new ExecutorProperties();

    @Data
    public static class ResponseIgnoreProperties {
        private Set<String> url = new HashSet<>();
    }


    /**
     *  异步线程池的配置
     */
    @Data
    public static class ExecutorProperties {

        /**
         *  核心线程数
         */
        private int poolSize = 5;

        /**
         *  最大线程数
         */
        private int maxSize = 10;

        /**
         *  队列长度
         */
        private int capacity = 100;

        /**
         *  线程最大存活时间
         */
        private int keepAlive = 1000;

        /**
         *  异步线程的前缀
         */
        private String prefix = "-async-server-";
    }
}
