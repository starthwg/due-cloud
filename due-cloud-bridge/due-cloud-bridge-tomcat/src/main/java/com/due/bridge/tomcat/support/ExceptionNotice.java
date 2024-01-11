package com.due.bridge.tomcat.support;

/**
 * 异常通知
 */
public interface ExceptionNotice {

    /**
     * 支持处理的异常类型
     *
     * @param e 异常类型
     * @return true 支持，false 不支持
     */
    boolean support(Exception e);


    /**
     * 执行通过
     *
     * @param notice 异常类型
     */
    void notice(UnknownExecoptionNotice notice);
}
