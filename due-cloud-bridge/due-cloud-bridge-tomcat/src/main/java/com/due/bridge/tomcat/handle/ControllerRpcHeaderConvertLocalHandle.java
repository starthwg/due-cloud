package com.due.bridge.tomcat.handle;


import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
            log.info(" ControllerRpcHeaderConvertLocalHandle -> 将请求头中的数据设置在threadLocal中：{}", header);
            // 将请求头中的数据set到threadLocal中
            ThreadLocalUtil.setDueRequest(header);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 删除
        log.info("ControllerRpcHeaderConvertLocalHandle -> 清楚threadLocal中的dueRequestData数据");
        ThreadLocalUtil.removeDueRequest();
    }
}
