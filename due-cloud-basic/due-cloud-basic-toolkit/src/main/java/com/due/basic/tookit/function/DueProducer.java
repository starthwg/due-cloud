package com.due.basic.tookit.function;

/**
 * 函数式接口
 *
 * @author hanwengang
 */
@FunctionalInterface
public interface DueProducer<T> {

    /**
     * 返回一个结果
     *
     */
    T apply();
}
