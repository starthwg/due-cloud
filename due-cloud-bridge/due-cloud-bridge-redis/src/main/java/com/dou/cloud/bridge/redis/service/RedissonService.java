package com.dou.cloud.bridge.redis.service;

import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.function.DueVoidProducer;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 操作redisson的service
 */
public class RedissonService {

    private final RedissonClient redissonClient;


    public RedissonService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 加锁
     *
     * @param key       锁的前缀
     * @param leaseTime 持有锁的时间
     * @param unit      单位
     * @param supplier  业务逻辑
     * @param <T>       返回值类型
     * @return 返回
     */
    public <T> T lock(String key, long leaseTime, TimeUnit unit, Supplier<T> supplier) {
        return Optional.ofNullable(supplier).map(e -> {
            RLock lock = redissonClient.getLock(key);
            try {
                lock.lock(leaseTime, unit);
                return e.get();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread())
                    lock.unlock();
            }
        }).orElse(null);
    }

    /**
     * 加锁
     *
     * @param key      锁的前缀
     * @param supplier 执行的业务逻辑
     * @param <T>      返回值类型
     * @return 返回值
     */
    public <T> T lock(String key, Supplier<T> supplier) {
        return this.lock(key, -1, null, supplier);
    }

    /**
     * 加锁
     *
     * @param key                 锁的前缀
     * @param leaseTime           持有锁的时间
     * @param unit                单位
     * @param voidCallBackHandler 业务逻辑
     */
    public void lock(String key, long leaseTime, TimeUnit unit, DueVoidProducer voidCallBackHandler) {
        Optional.ofNullable(voidCallBackHandler).ifPresent(e -> {
            RLock lock = redissonClient.getLock(key);
            try {
                lock.lock(leaseTime, unit);
                voidCallBackHandler.exec();
            } catch (Exception ex) {
                throw new LogicException(ex);
            } finally {
                if (null != lock && lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        });
    }

    public void lock(String key, DueVoidProducer voidCallBackHandler) {
        Optional.ofNullable(voidCallBackHandler).ifPresent(e -> {
            RLock lock = redissonClient.getLock(key);
            try {
                lock.lock(-1, null);
                voidCallBackHandler.exec();
            } catch (Exception ex) {
                throw new LogicException(ex);
            } finally {
                if (null != lock && lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        });
    }

    /**
     * 支持获取到才会往下执行
     *
     * @param key      前缀
     * @param permits  获取的数量
     * @param supplier 执行的业务逻辑
     * @param <T>      返回值类型
     * @return 返回值
     */
    public <T> T semaphoreAcquire(String key, int permits, Supplier<T> supplier) {
        return Optional.ofNullable(supplier)
                .map(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    try {
                        //
                        semaphore.acquire(permits);
                        return supplier.get();
                    } catch (InterruptedException ex) {
                        throw new LogicException(ex);
                    }
                }).orElse(null);
    }

    public <T> T semaphoreAcquire(String key, Supplier<T> supplier) {
        return Optional.ofNullable(supplier)
                .map(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    try {
                        //
                        semaphore.acquire(1);
                        return supplier.get();
                    } catch (InterruptedException ex) {
                        throw new LogicException(ex);
                    }
                }).orElse(null);
    }

    public void semaphoreAcquire(String key, int permits, DueVoidProducer voidCallBackHandler) {
        Optional.ofNullable(voidCallBackHandler)
                .ifPresent(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    try {
                        semaphore.acquire(permits);
                        voidCallBackHandler.exec();
                    } catch (InterruptedException ex) {
                        throw new LogicException(ex);
                    }
                });
    }

    public void semaphoreAcquire(String key, DueVoidProducer voidCallBackHandler) {
        Optional.ofNullable(voidCallBackHandler)
                .ifPresent(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    try {
                        semaphore.acquire(1);
                        voidCallBackHandler.exec();
                    } catch (InterruptedException ex) {
                        throw new LogicException(ex);
                    }
                });
    }

    /**
     * 支持获取到才会往下执行
     *
     * @param key      前缀
     * @param permits  获取的数量
     * @param supplier 执行的业务逻辑
     * @param <T>      返回值类型
     * @return 返回值
     */
    public <T> T semaphoreTryAcquire(String key, int permits, Supplier<T> supplier) {
        return Optional.ofNullable(supplier)
                .map(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    boolean updated = semaphore.tryAcquire(permits);
                    if (updated) {
                        return supplier.get();
                    } else {
                        return null;
                    }
                }).orElse(null);
    }

    public <T> T semaphoreTryAcquire(String key, Supplier<T> supplier) {
        return Optional.ofNullable(supplier)
                .map(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    boolean updated = semaphore.tryAcquire();
                    if (updated) {
                        return supplier.get();
                    } else {
                        return null;
                    }
                }).orElse(null);
    }

    public void semaphoreTryAcquire(String key, int permits, DueVoidProducer voidCallBackHandler) {
        Optional.ofNullable(voidCallBackHandler)
                .ifPresent(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    boolean updated = semaphore.tryAcquire(permits);
                    if (updated) {
                        voidCallBackHandler.exec();
                    } else {
                        return;
                    }
                });
    }

    public void semaphoreTryAcquire(String key, DueVoidProducer voidCallBackHandler) {
        Optional.ofNullable(voidCallBackHandler)
                .ifPresent(e -> {
                    RSemaphore semaphore = this.redissonClient.getSemaphore(key);
                    if (semaphore.tryAcquire()) {
                        voidCallBackHandler.exec();
                    }
                });
    }

}
