package com.due.cloud.bridge.file.support;

/**
 * 抽象的文件操作类
 *
 * @param <T> 操作客户端
 * @author hanwengang
 */
public class AbstractFileTemplate<T> implements FileTemplate {


    /**
     * 客户端
     */
    private T client;
}
