package com.dou.cloud.bridge.redis.service;


import com.due.basic.tookit.doamin.PageSet;
import com.due.basic.tookit.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 设置缓存失效时间
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key)) return false;
        if (unit == null) unit = TimeUnit.SECONDS;
        try {
            return redisTemplate.expire(key, timeout, unit);
        } catch (Exception e) {
            log.error("设置缓存失效时间失败：{}", key, e);
            return false;
        }
    }

    /**
     * 设置缓存失效时间（默认秒）
     *
     * @param key
     * @param timeout
     * @return
     */
    public boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存失效时间
     *
     * @param key
     * @param unit
     * @return
     */
    public long getExpire(String key, TimeUnit unit) {
        if (StringUtils.isBlank(key)) return -1L;
        if (unit == null) unit = TimeUnit.SECONDS;
        try {
            return redisTemplate.getExpire(key, unit);
        } catch (Exception e) {
            log.error("获取缓存失效时间失败：{}", key, e);
            return -1l;
        }
    }

    /**
     * 获取缓存失效时间（默认秒）
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断缓存中是否含有key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        if (StringUtils.isBlank(key)) return false;
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断缓存中是否含有数据失败：{}", key, e);
            return false;
        }
    }

    /**
     * 获取指定的缓存数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        if (StringUtils.isBlank(key)) return null;
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("获取缓存数据失败：{}", key, e);
            return null;
        }
    }

    /**
     * 获取指定的缓存数据
     *
     * @param key
     * @param <V>
     * @return
     */
    public <V> V getObject(String key) {
        Object obj = get(key);
        if (obj == null) return null;
        return (V) obj;
    }

    /**
     * 设置缓存数据
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public <V> boolean set(String key, V value, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key)) return false;
        if (unit == null) unit = TimeUnit.SECONDS;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            return true;
        } catch (Exception e) {
            log.error("设置缓存数据失败：{}={}", key, value, e);
            return false;
        }
    }

    /**
     * 设置缓存数据
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public <V> boolean set(String key, V value, long timeout) {
        return set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存数据
     *
     * @param key
     * @param value
     * @return
     */
    public <V> boolean set(String key, V value) {
        if (StringUtils.isBlank(key)) return false;
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置缓存数据失败：{}={}", key, value, e);
            return false;
        }
    }

    /**
     * 删除缓存数据
     *
     * @param keys
     * @return
     */
    public boolean delete(String... keys) {
        if (keys == null || keys.length == 0) return false;
        try {
            return redisTemplate.delete(Arrays.stream(keys).collect(Collectors.toList())) > 0;
        } catch (Exception e) {
            log.error("删除缓存数据失败：{}", keys);
            return false;
        }
    }


    /**
     * 根据通配redis缓存key删除缓存数据
     *
     * @param keys 通配缓存key
     * @return 删除结果
     */
    public boolean deleteByPattern(String... keys) {
        Set<String> allKeys = getKeyByPattern(keys);
        if (CollectionUtils.isEmpty(allKeys)) return false;
        try {
            return redisTemplate.delete(allKeys) > 0;
        } catch (Exception e) {
            log.error("删除缓存数据失败：{}", keys);
            return false;
        }
    }

    public Set<String> getKeyByPattern(String... keys) {
        if (keys == null || keys.length == 0) return null;
        Set<String> allKeys = new ConcurrentSkipListSet<>();
        for (String key : keys) {
            Set<String> set = redisTemplate.keys(key);
            if (CollectionUtils.isEmpty(set)) continue;
            allKeys.addAll(set);
        }
        return allKeys;
    }

    /**
     * 缓存数据递增
     *
     * @param key
     * @param delta
     * @return
     */
    public long increment(String key, long delta) {
        if (StringUtils.isBlank(key)) return -1l;
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("缓存数据递增失败：{}", key, e);
            return -1l;
        }
    }

    /**
     * 缓存数据递增
     *
     * @param key
     * @param delta
     * @return
     */
    public double increment(String key, double delta) {
        if (StringUtils.isBlank(key)) return -1d;
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("缓存数据递增失败：{}", key, e);
            return -1d;
        }
    }

    /**
     * 缓存数据递减
     *
     * @param key
     * @param delta
     * @return
     */
    public long decrement(String key, long delta) {
        return increment(key, -delta);
    }

    /**
     * 缓存数据递减
     *
     * @param key
     * @param delta
     * @return
     */
    public double decrement(String key, double delta) {
        return increment(key, -delta);
    }

    /**
     * 判断缓存hash表里是否有数据
     *
     * @param key
     * @param item
     * @return
     */
    public <HK> boolean hHasKey(String key, HK item) {
        if (StringUtils.isBlank(key) || item == null) return false;
        try {
            return redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            log.error("判断缓存hash表里是否有数据失败：{}->{}", key, item, e);
            return false;
        }
    }

    /**
     * 获取缓存hash数据
     *
     * @param key
     * @param item
     * @return
     */
    public <HK, HV> HV hGet(String key, HK item) {
        if (StringUtils.isBlank(key) || item == null) return null;
        try {
            HashOperations<String, HK, HV> operations = redisTemplate.opsForHash();
            return operations.get(key, item);
        } catch (Exception e) {
            log.error("获取缓存hash数据失败：{}->{}", key, item, e);
            return null;
        }
    }

    /**
     * 获取缓存hash数据集合
     *
     * @param key
     * @return
     */
    public <HK, HV> Map<HK, HV> hmGet(String key) {
        if (StringUtils.isBlank(key)) return null;
        try {
            HashOperations<String, HK, HV> operations = redisTemplate.opsForHash();
            return operations.entries(key);
        } catch (Exception e) {
            log.error("获取缓存hash数据失败：{}", key, e);
            return null;
        }
    }

    /**
     * 获取缓存hash数据
     *
     * @param key
     * @param items
     * @return
     */
    public <HK, HV> List<HV> hmGet(String key, List<HK> items) {
        if (StringUtils.isBlank(key)) return null;
        try {
            HashOperations<String, HK, HV> operations = redisTemplate.opsForHash();
            return operations.multiGet(key, items);
        } catch (Exception e) {
            log.error("获取缓存hash数据失败：{}", key, e);
            return null;
        }
    }

    /**
     * 设置缓存hash数据
     *
     * @param key
     * @param item
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public <HK, HV> boolean hSet(String key, HK item, HV value, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || item == null) return false;
        if (unit == null) unit = TimeUnit.SECONDS;
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return expire(key, timeout, unit);
        } catch (Exception e) {
            log.error("设置缓存hash数据失败：{}->{}={}", key, item, value, e);
            return false;
        }
    }

    /**
     * 设置缓存hash数据
     *
     * @param key
     * @param item
     * @param value
     * @param timeout
     * @return
     */
    public <HK, HV> boolean hSet(String key, HK item, HV value, long timeout) {
        return hSet(key, item, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存hash数据
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public <HK, HV> boolean hSet(String key, HK item, HV value) {
        if (StringUtils.isBlank(key) || item == null) return false;
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("设置缓存hash数据失败：{}->{}={}", key, item, value, e);
            return false;
        }
    }

    /**
     * 设置缓存hash数据
     *
     * @param key
     * @param values
     * @param timeout
     * @return
     */
    public <HK, HV> boolean hmSet(String key, Map<HK, HV> values, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || values == null || values.isEmpty()) return false;
        if (unit == null) unit = TimeUnit.SECONDS;
        try {
            redisTemplate.opsForHash().putAll(key, values);
            return expire(key, timeout, unit);
        } catch (Exception e) {
            log.error("设置缓存hash数据失败：{}->{}", key, values, e);
            return false;
        }
    }

    /**
     * 设置缓存hash数据
     *
     * @param key
     * @param values
     * @param timeout
     * @return
     */
    public <HK, HV> boolean hmSet(String key, Map<HK, HV> values, long timeout) {
        return hmSet(key, values, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存hash数据
     *
     * @param key
     * @param values
     * @return
     */
    public <HK, HV> boolean hmSet(String key, Map<HK, HV> values) {
        if (StringUtils.isBlank(key) || values == null || values.isEmpty()) return false;
        try {
            redisTemplate.opsForHash().putAll(key, values);
            return true;
        } catch (Exception e) {
            log.error("设置缓存hash数据失败：{}->{}", key, values, e);
            return false;
        }
    }

    /**
     * 删除缓存hash数据
     *
     * @param key
     * @param items
     * @return
     */
    public <HK> boolean hDelete(String key, HK... items) {
        if (StringUtils.isBlank(key)) return false;
        try {
            redisTemplate.opsForHash().delete(key, items);
            return true;
        } catch (Exception e) {
            log.error("删除缓存hash数据失败：{}->{}", key, items, e);
            return false;
        }
    }

    /**
     * 缓存hash表数据递增（如果不存在就会创建一个，并把新增的值返回）
     *
     * @param key
     * @param item
     * @param delta
     * @return
     */
    public <HK> long hIncrement(String key, HK item, long delta) {
        if (StringUtils.isBlank(key) || item == null) return -1l;
        try {
            return redisTemplate.opsForHash().increment(key, item, delta);
        } catch (Exception e) {
            log.error("缓存hash表数据递增失败：{}->{}", key, item, e);
            return -1l;
        }
    }

    /**
     * 缓存hash表数据递增（如果不存在就会创建一个，并把新增的值返回）
     *
     * @param key
     * @param item
     * @param delta
     * @return
     */
    public <HK> double hIncrement(String key, HK item, double delta) {
        if (StringUtils.isBlank(key) || item == null) return -1l;
        try {
            return redisTemplate.opsForHash().increment(key, item, delta);
        } catch (Exception e) {
            log.error("缓存hash表数据递增失败：{}->{}", key, item, e);
            return -1l;
        }
    }

    /**
     * 缓存hash表数据递减（如果不存在就会创建一个，并把减少的值返回）
     *
     * @param key
     * @param item
     * @param delta
     * @return
     */
    public <HK> long hDecrement(String key, HK item, long delta) {
        return hIncrement(key, item, -delta);
    }

    /**
     * 缓存hash表数据递减（如果不存在就会创建一个，并把减少的值返回）
     *
     * @param key
     * @param item
     * @param delta
     * @return
     */
    public <HK> double hDecrement(String key, HK item, double delta) {
        return hIncrement(key, item, -delta);
    }

    /**
     * 判断缓存Set中是否有数据
     *
     * @param key
     * @param value
     * @return
     */
    public <V> boolean sHasKey(String key, V value) {
        if (StringUtils.isBlank(key) || value == null) return false;
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("判断缓存Set中是否有数据失败：{}->{}", key, value, e);
            return false;
        }
    }

    /**
     * 获取缓存Set数据
     *
     * @param key
     * @return
     */
    public <V> Set<V> sGet(String key) {
        if (StringUtils.isBlank(key)) return null;
        try {
            Set<Object> set = redisTemplate.opsForSet().members(key);
            if (set == null) return null;
            return set.stream().map(e -> (V) e).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("获取缓存Set数据失败：{}", key, e);
            return null;
        }
    }

    /**
     * 设置缓存Set数据
     *
     * @param key
     * @param timeout
     * @param unit
     * @param values
     * @return
     */
    public <V> boolean sAdd(String key, long timeout, TimeUnit unit, V... values) {
        if (StringUtils.isBlank(key)) return false;
        try {
            long count = redisTemplate.opsForSet().add(key, values);
            if (count > 0) expire(key, timeout, unit);
            return count > 0;
        } catch (Exception e) {
            log.error("设置缓存Set数据失败：{}->{}", key, values, e);
            return false;
        }
    }

    /**
     * 设置缓存Set数据
     *
     * @param key
     * @param timeout
     * @param values
     * @return
     */
    public <V> boolean sAdd(String key, long timeout, V... values) {
        return sAdd(key, timeout, TimeUnit.SECONDS, values);
    }

    /**
     * 设置缓存Set数据
     *
     * @param key
     * @param values
     * @return
     */
    public <V> boolean sAdd(String key, V... values) {
        if (StringUtils.isBlank(key)) return false;
        try {
            return redisTemplate.opsForSet().add(key, values) > 0;
        } catch (Exception e) {
            log.error("设置缓存Set数据失败：{}->{}", key, values, e);
            return false;
        }
    }

    /**
     * 获取缓存Set的长度
     *
     * @param key
     * @return
     */
    public long sSize(String key) {
        if (StringUtils.isBlank(key)) return 0;
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("获取缓存Set的长度失败：{}", key, e);
            return 0;
        }
    }

    /**
     * 获取缓存List中的数据
     *
     * @param key
     * @param index
     * @return
     */
    public <V> V lGet(String key, long index) {
        if (StringUtils.isBlank(key)) return null;
        try {
            return (V) redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("获取缓存List中的数据失败：{}", key, e);
            return null;
        }
    }

    /**
     * 获取缓存List中的数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <V> List<V> lGet(String key, long start, long end) {
        if (StringUtils.isBlank(key)) return null;
        try {
            List<Object> list = redisTemplate.opsForList().range(key, start, end);
            if (list == null) return null;
            return list.stream().map(e -> (V) e).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("获取缓存List中的数据失败：{}", key, e);
            return null;
        }
    }

    /**
     * 获取缓存List的大小
     *
     * @param key
     * @return
     */
    public long lSize(String key) {
        if (StringUtils.isBlank(key)) return 0l;
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("获取缓存List的大小失败：{}", key, e);
            return 0l;
        }
    }

    /**
     * 设置缓存List中的数据失败
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public <V> boolean lAdd(String key, V value, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || value == null) return false;
        if (unit == null) unit = TimeUnit.SECONDS;
        try {
            long index = redisTemplate.opsForList().rightPush(key, value);
            if (index >= 0) expire(key, timeout, unit);
            return index >= 0;
        } catch (Exception e) {
            log.error("设置缓存List中的数据失败：{}", key, e);
            return false;
        }
    }

    /**
     * 设置缓存List中的数据失败
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public <V> boolean lAdd(String key, V value, long timeout) {
        return lAdd(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存List中的数据失败
     *
     * @param key
     * @param value
     * @return
     */
    public <V> boolean lAdd(String key, V value) {
        if (StringUtils.isBlank(key) || value == null) return false;
        try {
            return redisTemplate.opsForList().rightPush(key, value) >= 0;
        } catch (Exception e) {
            log.error("设置缓存List中的数据失败：{}", key, e);
            return false;
        }
    }

    /**
     * 设置缓存List中的数据失败
     *
     * @param key
     * @param values
     * @param timeout
     * @param unit
     * @return
     */
    public <V> boolean lAdd(String key, List<V> values, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || values == null || values.isEmpty()) return false;
        if (unit == null) unit = TimeUnit.SECONDS;
        try {
            long index = redisTemplate.opsForList().rightPushAll(key, values);
            if (index >= 0) expire(key, timeout, unit);
            return index >= 0;
        } catch (Exception e) {
            log.error("设置缓存List中的数据失败：{}", key, e);
            return false;
        }
    }

    /**
     * 设置缓存List中的数据失败
     *
     * @param key
     * @param values
     * @param timeout
     * @return
     */
    public <V> boolean lAdd(String key, List<V> values, long timeout) {
        return lAdd(key, values, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存List中的数据失败
     *
     * @param key
     * @param values
     * @return
     */
    public <V> boolean lAdd(String key, List<V> values) {
        if (StringUtils.isBlank(key) || values == null || values.isEmpty()) return false;
        try {
            return redisTemplate.opsForList().rightPushAll(key, values) >= 0;
        } catch (Exception e) {
            log.error("设置缓存List中的数据失败：{}", key, e);
            return false;
        }
    }

    /**
     * 更新缓存List中的数据
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    public <V> boolean lUpdate(String key, long index, V value) {
        if (StringUtils.isBlank(key) || value == null) return false;
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("更新缓存List中的数据失败：{}", key);
            return false;
        }
    }

    /**
     * 删除缓存List中的指定数量的数据
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public <V> boolean lDelete(String key, long count, V value) {
        if (StringUtils.isBlank(key) || value == null) return false;
        try {
            return redisTemplate.opsForList().remove(key, count, value) >= 0;
        } catch (Exception e) {
            log.error("删除缓存List中的数据失败");
            return false;
        }
    }

    /**
     * 获取缓存ZSet有序集合大小
     *
     * @param key
     * @return
     */
    public long zSize(String key) {
        if (StringUtils.isBlank(key)) return 0l;
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            log.error("获取缓存ZSet有序集合大小失败：{}", key, e);
            return 0l;
        }
    }

    public <V> Set<V> zGet(String key, double score1, double score2) {
        if (StringUtils.isBlank(key)) return null;
        try {
            Set<Object> set = redisTemplate.opsForZSet().rangeByScore(key, score1, score2);
            if (set == null) return null;
            return set.stream().map(e -> (V) e).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("获取缓存ZSet有序集合数据失败");
            return null;
        }
    }

    public <V> Set<V> zGet(String key, long start, long end) {
        if (StringUtils.isBlank(key)) return null;
        try {
            Set<Object> set = redisTemplate.opsForZSet().range(key, start, end);
            if (set == null) return null;
            return set.stream().map(e -> (V) e).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("获取缓存ZSet有序集合数据失败");
            return null;
        }
    }

    public <V> PageSet<V> zGet(String key, PageSet<V> page) {
        if (StringUtils.isBlank(key)) return null;
        if (page == null) page = new PageSet<>();
        long start = (page.getStart() <= 0 ? 0 : (page.getStart() / page.getLimit())) + 1;
        long end = start + page.getLimit() - 1;
        if (end > zSize(key)) end = -1;
        Set<V> set = zGet(key, start, end);
        page.setTotal(zSize(key));
        if (set != null && !set.isEmpty()) page.setRecords(set.stream().collect(Collectors.toList()));
        return page;
    }

    /**
     * 添加缓存ZSet有序集合
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public <V> boolean zAdd(String key, V value, double score) {
        if (StringUtils.isBlank(key) || value == null) return false;
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("添加缓存ZSet有序集合失败：{}", key, e);
            return false;
        }
    }

    /**
     * 删除缓存ZSet中的值
     *
     * @param key
     * @param value
     * @return
     */
    public <V> boolean zDelete(String key, V value) {
        if (StringUtils.isBlank(key) || value == null) return false;
        try {
            return redisTemplate.opsForZSet().remove(key, value) > 0;
        } catch (Exception e) {
            log.error("删除缓存ZSet有序集合失败：{}", key, e);
            return false;
        }
    }

    /**
     * 上锁
     *
     * @param key   一般设定为lock
     * @param value 一般使用uid
     * @param time  缓存时间
     * @return 上锁结果
     */
    public boolean setNx(String key, String value, int time) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS));
    }

    /**
     * 上锁
     *
     * @param key   一般设定为lock
     * @param value 一般使用uid
     * @return 上锁结果
     */
    public boolean setNx(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
    }

    /**
     * 周期性获取自增long型值
     *
     * @param key  主键key
     * @param time 周期时间
     * @param unit 时间单位
     * @return long值
     */
    public long incrementAndGetLong(String key, int time, TimeUnit unit) {
        long value = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, time, unit);
        return value;
    }

    /**
     * 周期性获取自增long型值
     *
     * @param key     主键key
     * @param expired 过期时间
     * @return long值
     */
    public long incrementAndGetLong(String key, Date expired) {
        long value = redisTemplate.opsForValue().increment(key);
        redisTemplate.expireAt(key, expired);
        return value;
    }

    /**
     * 按分钟获取自增long型值
     *
     * @param key 主键key
     * @return long值
     */
    public long incrementAndGetLongByMinute(String key) {
        return incrementAndGetLong(key, 1, TimeUnit.MINUTES);
    }

    /**
     * 按小时获取自增long型值
     *
     * @param key 主键key
     * @return long值
     */
    public long incrementAndGetLongByHour(String key) {
        return incrementAndGetLong(key, 1, TimeUnit.HOURS);
    }

    /**
     * 按天获取自增long型值
     *
     * @param key 主键key
     * @return long值
     */
    public long incrementAndGetLongByDay(String key) {
        return incrementAndGetLong(key, 1, TimeUnit.DAYS);
    }

    /**
     * 按周获取自增long型值
     *
     * @param key 主键key
     * @return long值
     */
    public long incrementAndGetLongByWeek(String key) {
        return incrementAndGetLong(key, DateUtil.lastDayOfWeek());
    }

    /**
     * 按月获取自增long型值
     *
     * @param key 主键key
     * @return long值
     */
    public long incrementAndGetLongByMonth(String key) {
        return incrementAndGetLong(key, DateUtil.lastDayOfMonth());
    }

    public static RedisAtomicLong redisAtomicLongByMinute(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        redisAtomicLong.expire(1, TimeUnit.MINUTES);
        return redisAtomicLong;
    }

    public static RedisAtomicLong redisAtomicLongByHour(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        redisAtomicLong.expire(1, TimeUnit.HOURS);
        return redisAtomicLong;
    }

    public static RedisAtomicLong redisAtomicLongByDay(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        redisAtomicLong.expire(1, TimeUnit.DAYS);
        return redisAtomicLong;
    }

    public static RedisAtomicLong redisAtomicLongByWeek(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        redisAtomicLong.expireAt(DateUtil.lastDayOfWeek());
        return redisAtomicLong;
    }

    public static RedisAtomicLong redisAtomicLongByMonth(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        redisAtomicLong.expireAt(DateUtil.lastDayOfMonth());
        return redisAtomicLong;
    }

    public static RedisAtomicInteger redisAtomicIntegerByMinute(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        redisAtomicInteger.expire(1, TimeUnit.MINUTES);
        return redisAtomicInteger;
    }

    public static RedisAtomicInteger redisAtomicIntegerByHour(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        redisAtomicInteger.expire(1, TimeUnit.HOURS);
        return redisAtomicInteger;
    }

    public static RedisAtomicInteger redisAtomicIntegerByDay(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        redisAtomicInteger.expire(1, TimeUnit.DAYS);
        return redisAtomicInteger;
    }

    public static RedisAtomicInteger redisAtomicIntegerByWeek(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        redisAtomicInteger.expireAt(DateUtil.lastDayOfWeek());
        return redisAtomicInteger;
    }

    public static RedisAtomicInteger redisAtomicIntegerByMonth(RedisTemplate<String, Object> redisTemplate, String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        redisAtomicInteger.expireAt(DateUtil.lastDayOfMonth());
        return redisAtomicInteger;
    }

}
