package com.due.basic.setting.logger;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * 日志切面
 */
@Aspect
@Slf4j
public class LoggingAspect {


    /**
     * 方法默认的接收的最长的执行时间
     */
    private static final Long DEFAULT_EXEC_TIME = 3000L;


    /**
     *
     */
    @Pointcut("execution(* com.due.cloud..service.*.*(..))")
    public void pointCut() {
    }

    /**
     * service执行之前
     */
    @Before(value = "pointCut()")
    public void execBefore(JoinPoint joinPoint) {
        long threadId = this.getThreadId();
        log.debug("{} - ==============================================================================================", threadId);
        log.debug("{} - method : {}.{}", threadId, joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        String string = Arrays.toString(joinPoint.getArgs());
        log.debug("{} - arguments : {}", threadId, string);
        log.debug("{} - ==============================================================================================", threadId);
    }

    @AfterReturning(returning = "result", pointcut = "pointCut()")
    public void execAfter(Object result) {
        log.debug("{} - result : {}", this.getThreadId(), JSONObject.toJSONString(result));
    }

    @Around(value = "pointCut()")
    public Object execAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        Object proceed = joinPoint.proceed(args);
        long execTime = System.currentTimeMillis() - start;
        if (execTime > DEFAULT_EXEC_TIME) {
            log.warn("{} - method execute : {} millisecond and execute time >= {} millis", this.getThreadId(), execTime, DEFAULT_EXEC_TIME);
        } else {
            log.debug("{} - method execute : {} millisecond ", this.getThreadId(), execTime);
        }
        return proceed;
    }

    private long getThreadId() {
        return Thread.currentThread().getId();
    }
}
