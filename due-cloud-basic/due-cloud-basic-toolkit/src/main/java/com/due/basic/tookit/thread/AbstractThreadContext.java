package com.due.basic.tookit.thread;

import com.due.basic.tookit.utils.LogicUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 基类的线程上下问
 */
public abstract class AbstractThreadContext {


    /**
     * 获取线程的上下问
     *
     * @return
     */
    public abstract ThreadLocal<Map<String, Object>> getThreadContext();


    public void set(final String key, final Object value) {
        Map<String, Object> map = this.getThreadLocal();
        map.put(key, value);
        if (value instanceof String) {
            map.put((String) value, Thread.currentThread().getName());
        }
    }

    public Object get(String key) {
        if (LogicUtil.isAllBlank(key)) {
            return null;
        }
        Map<String, Object> map = this.getThreadLocal();
        return map.get(key);
    }

    public void removeKey(String key) {
        Map<String, Object> map = this.getThreadLocal();
        map.remove(key);
    }

    public void clear() {
        this.getThreadLocal().clear();
    }

    private Map<String, Object> getThreadLocal() {
        ThreadLocal<Map<String, Object>> threadContext = this.getThreadContext();
        Map<String, Object> map = threadContext.get();
        if (null == map) {
            map = new HashMap<>(8);
            threadContext.set(map);
        }
        return map;
    }
}
