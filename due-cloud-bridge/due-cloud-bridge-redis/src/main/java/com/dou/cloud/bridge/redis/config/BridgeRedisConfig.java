package com.dou.cloud.bridge.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 *  redis 的相关配置
 */
@EnableCaching
@Configuration
public class BridgeRedisConfig {

    @Autowired
    private RedisProperties redisProperties;


}
