package com.dou.cloud.bridge.redis.support;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.cache.CacheStatistics;
import org.springframework.data.redis.cache.CacheStatisticsCollector;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * 自定义cacheRedis的writer
 */
@Slf4j
public class DueLoggerCustomRedisCacheWriter implements RedisCacheWriter {

    private RedisCacheWriter redisCacheWriter;

    public RedisCacheWriter getRedisCacheWriter() {
        return redisCacheWriter;
    }

    public void setRedisCacheWriter(RedisCacheWriter redisCacheWriter) {
        this.redisCacheWriter = redisCacheWriter;
    }

    @Override
    public void put(@NotNull String name, @NotNull byte[] key, @NotNull byte[] value, Duration ttl) {
        String path = this.getRedisKey(key);
        log.info("设置缓存：缓存名字：{}，过期时间:{}", path, ttl);
        redisCacheWriter.put(name, key, value, ttl);
    }

    @Override
    public byte[] get(@NotNull String name, @NotNull byte[] key) {
        String path = this.getRedisKey(key);
        byte[] bytes = redisCacheWriter.get(name, key);
        boolean result = null != bytes && bytes.length != 0;
        log.info("获取：缓存名字：{}, 获取结果：{}", path, result);
        return bytes;
    }

    @Override
    public byte[] putIfAbsent(@NotNull String name, @NotNull byte[] key, @NotNull byte[] value, Duration ttl) {
        String redisKey = this.getRedisKey(key);
        log.info("准备设置缓存，如果不存在就设置，缓存名字：{}", redisKey);
        return new byte[0];
    }

    @Override
    public void remove(@NotNull String name, @NotNull byte[] key) {
        String redisKey = this.getRedisKey(key);
        log.debug("准备删除缓存：{}", redisKey);
        redisCacheWriter.remove(name, key);
    }

    @Override
    public void clean(@NotNull String name, @NotNull byte[] pattern) {
        redisCacheWriter.clean(name, pattern);
    }

    @Override
    public void clearStatistics(@NotNull String name) {
        log.debug("准备清楚数据统计：{}", name);
        redisCacheWriter.clearStatistics(name);
    }

    @NotNull
    @Override
    public RedisCacheWriter withStatisticsCollector(@NotNull CacheStatisticsCollector cacheStatisticsCollector) {
        return redisCacheWriter.withStatisticsCollector(cacheStatisticsCollector);
    }

    @NotNull
    @Override
    public CacheStatistics getCacheStatistics(@NotNull String cacheName) {
        log.info("获取数据统计：{}", cacheName);
        return redisCacheWriter.getCacheStatistics(cacheName);
    }


    private String getRedisKey(byte[] key) {
        return new String(key);
    }
}
