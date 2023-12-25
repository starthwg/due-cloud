package com.due.bridge.tomcat.handle;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;

/**
 * 打印controller日志
 */
@Slf4j
@Order(Integer.MIN_VALUE + 20)
public class ControllerLoggerHandle implements HandlerInterceptor {

    /**
     * 执行的时间
     */
    private Long startTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            this.startTime = System.currentTimeMillis();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            log.info("{} - ==========================================服务开始执行==============================================", this.getThreadId());
            log.info("{} - controller : {}.{} start Execution", this.getThreadId(), method.getDeclaringClass(), method.getName());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("结果：{}", JSONObject.toJSONString(modelAndView));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 处理
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            long endTime = System.currentTimeMillis() - this.startTime;
            if (ex == null) {
                log.info("{} - controller : {}.{} end Execution time : {} millisecond", this.getThreadId(), method.getDeclaringClass(), method.getName(), endTime);
            }else {
                log.info("{} - controller : {}.{} end Execution Exception", this.getThreadId(), method.getDeclaringClass(), method.getName());
            }
            ServletOutputStream outputStream = response.getOutputStream();
            log.info("{} - ==========================================服务结束执行==============================================", this.getThreadId());
        }
    }

    private long getThreadId() {
        return Thread.currentThread().getId();
    }
}
