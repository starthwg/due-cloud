package com.cloud.bridge.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 *  认证模块的配置
 * @author hanwengang
 */
@Data
@ConfigurationProperties(prefix = "due.auth")
public class AuthBridgeProperties {


    /**
     * 有效期
     */
    private Long expiration = 86400L;
}
