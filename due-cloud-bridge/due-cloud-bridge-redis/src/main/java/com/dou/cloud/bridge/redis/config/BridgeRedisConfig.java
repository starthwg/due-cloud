package com.dou.cloud.bridge.redis.config;

import com.dou.cloud.bridge.redis.service.RedisService;
import com.dou.cloud.bridge.redis.service.RedissonService;
import com.dou.cloud.bridge.redis.support.DueLoggerCustomRedisCacheWriter;
import com.dou.cloud.bridge.redis.support.FastJson2JsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * redis 的相关配置
 */
@EnableCaching
@Slf4j
@Configuration
public class BridgeRedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Value("${spring.application.name}")
    private String applicationName;


    private static final String REDISSON_URL_PREFIX = "redis://";

    /**
     * 配置redisson
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();

        if (null != this.redisProperties.getCluster()) {
            RedisProperties.Cluster cluster = this.redisProperties.getCluster();
            // 集群模式
            List<String> nodes = cluster.getNodes();
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            List<String> redissonNode = nodes.stream().map(e -> REDISSON_URL_PREFIX + e).collect(Collectors.toList());
            log.info("redisson开启集群模式，集群节点：{}", redissonNode);
            clusterServersConfig.setNodeAddresses(redissonNode);
            clusterServersConfig.setLoadBalancer(new RoundRobinLoadBalancer());
            clusterServersConfig.setTimeout((int) this.redisProperties.getTimeout().toMinutes());
            clusterServersConfig.setKeepAlive(true);
            clusterServersConfig.setRetryAttempts(5);
            clusterServersConfig.setRetryInterval(1500);
        } else if (null != this.redisProperties.getSentinel()) {
            RedisProperties.Sentinel sentinel = this.redisProperties.getSentinel();
            String master = sentinel.getMaster();
            List<String> nodes = sentinel.getNodes();
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(REDISSON_URL_PREFIX + master);
            sentinelServersConfig.setSentinelAddresses(nodes);
            log.info("redisson开启sentinel模式，集群节点;{}", nodes);
        } else {
            String host = this.redisProperties.getHost();
            int port = this.redisProperties.getPort();
            String password = this.redisProperties.getPassword();
            String address = REDISSON_URL_PREFIX + host + ":" + port;
            config.useSingleServer().setAddress(address)
                    .setKeepAlive(true).setPassword(password).setDatabase(this.redisProperties.getDatabase());
            log.info("redisson单机模式，节点：{}", address);
        }
        return Redisson.create(config);
    }

    /**
     * 设置redis的客户端
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // value的方式
        FastJson2JsonRedisSerializer<Object> fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer<Object>(Object.class);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // 使用json的序列化方式
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
        // 设置hash结构的key也是string类型
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        return redisTemplate;
    }

    /**
     * 设置stringRedisTemplate
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringRedisTemplate.setKeySerializer(RedisSerializer.string());
        return stringRedisTemplate;
    }


    /**
     * 操作redisson的类型
     */
    @Bean
    public RedissonService redissonService() {
        return new RedissonService(this.redissonClient());
    }

    /**
     * redis 的服务类
     */
    @Bean
    public RedisService redisService(RedisConnectionFactory redisConnectionFactory) {
        return new RedisService(this.redisTemplate(redisConnectionFactory));
    }

    /**
     * 缓存管理器配置
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        DueLoggerCustomRedisCacheWriter dueLoggerCustomRedisCacheWriter = new DueLoggerCustomRedisCacheWriter();
        dueLoggerCustomRedisCacheWriter.setRedisCacheWriter(redisCacheWriter);
        return RedisCacheManager.builder()
                // 设置打印logger的缓存写入器
                .cacheWriter(dueLoggerCustomRedisCacheWriter)
                .cacheDefaults(
                        RedisCacheConfiguration
                                .defaultCacheConfig()
                                .disableCachingNullValues() // 禁用空值
                                // 缓存的统一前缀
                                .computePrefixWith(cacheName -> String.format("%s:%s", applicationName, cacheName))
                                // 缓存的过期时间
                                .entryTtl(Duration.ofMillis(43200000L))
                                // 设置缓存value的序列化方式
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json())))
                .build();
    }
}
