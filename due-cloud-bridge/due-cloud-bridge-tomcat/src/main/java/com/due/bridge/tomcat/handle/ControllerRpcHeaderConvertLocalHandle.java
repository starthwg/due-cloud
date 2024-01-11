package com.due.bridge.tomcat.handle;


import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 将请求头头中的dueRequest转化成threadLocal
 */
@Order(Integer.MIN_VALUE + 50)
@Slf4j
public class ControllerRpcHeaderConvertLocalHandle implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(GlobalConstant.PROJECT_BASE_PATH)) {
            return true;
        }
        String header = request.getHeader(GlobalConstant.DUE_RPC_MODULE_REQUEST);
        if (LogicUtil.isAllNotBlank(header)) {
            String decode = URLDecoder.decode(header, "UTF-8");
            // 将请求头中的数据set到threadLocal中
            ThreadLocalUtil.setDueRequest(decode);
            log.info("{} - 服务调调用方：{} - 使用场景：{} - 用户Id：{} - 统一流水号：{}", this.getThreadId(), ThreadLocalUtil.getRequestService(), ThreadLocalUtil.getServiceCode(), ThreadLocalUtil.getMemberId(), ThreadLocalUtil.getSerialNo() );
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.removeDueRequest();
    }
    private Long getThreadId() {
        return Thread.currentThread().getId();
    }
}
