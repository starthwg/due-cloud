package com.due.bridge.tomcat.handle;

import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.enums.ChannelEnum;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * /due请求开头的请求，在treadLocal中添加流水号，渠道类型等
 *
 * @author hanwengang
 */
@Order(Integer.MIN_VALUE + 30)
@Slf4j
public class ControllerDueHandle implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(GlobalConstant.PROJECT_BASE_PATH)) {
            log.info("ControllerDueHandle ---> 设置唯一流水号");
            ThreadLocalUtil.set(GlobalThreadLocalConstant.SERIAL_NO, UUID.randomUUID().toString());
        }
        if (requestURI.startsWith(GlobalConstant.PROJECT_APP_PATH)) {
            log.info("ControllerDueHandle --> 设置渠道类型：{}", ChannelEnum.APP);
            ThreadLocalUtil.set(GlobalThreadLocalConstant.CHANNEL_ENUM, ChannelEnum.APP);
        } else if (requestURI.startsWith(GlobalConstant.PROJECT_BACK_PATH)) {
            log.info("ControllerDueHandle --> 设置渠道类型：{}", ChannelEnum.BACK);
            ThreadLocalUtil.set(GlobalThreadLocalConstant.CHANNEL_ENUM, ChannelEnum.BACK);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.removeDueRequest();
    }
}
