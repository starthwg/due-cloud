package com.due.cloud.bridge.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 *  资源配置信息
 * @author hanwengang
 */
@Data
@ConfigurationProperties(prefix = "due.resource")
public class DueResourcesProperties {

    /**
     *  资源配置
     */
    private Auth auth;

    @Data
    public static class Auth {

        /**
         *  不登录可以访问的url
         */
        private Set<String> urls;

        /**
         *  认证地址
         */
        private String authAddress;

        /**
         *  请求方式
         */
        private String authMethod;
    }
}
