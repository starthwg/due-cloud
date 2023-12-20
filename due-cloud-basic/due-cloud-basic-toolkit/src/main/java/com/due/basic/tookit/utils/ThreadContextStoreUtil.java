package com.due.basic.tookit.utils;

import com.due.basic.tookit.thread.AbstractThreadContext;

import java.util.Map;

public class ThreadContextStoreUtil extends AbstractThreadContext {

    /**
     * 保存线程上下午内容
     */
    private static ThreadLocal<Map<String, Object>> threadContext = new ThreadLocal<>();

    /**
     *  单利模式中的变量
     */
    private volatile static ThreadContextStoreUtil instance;


    private static final Object LOCK = new Object();


    public static ThreadContextStoreUtil getInstance() {
        if (null != instance) {
            return instance;
        }
        synchronized (LOCK) {
            if (null != instance) return instance;
            instance = new ThreadContextStoreUtil();
            return instance;
        }
    }


    @Override
    public ThreadLocal<Map<String, Object>> getThreadContext() {
        return threadContext;
    }
}
