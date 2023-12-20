package com.due.bridge.tomcat.handle;

import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicException;
import com.due.bridge.tomcat.support.ExceptionNotice;
import com.due.bridge.tomcat.support.UnknownExecoptionNotice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * 处理controller异常的
 */
@Slf4j
@RestControllerAdvice
public class ControllerErrorHandle {




    @Autowired
    private Environment environment;


    /**
     * 处理未知异常通知
     */
    @Autowired
    private ExceptionNotice exceptionNotice;


    /**
     * 处理指项目自定义异常
     */
    @ExceptionHandler(LogicException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handleLogicException(LogicException exception) {
        log.error(exception.getMessage(), exception);
        return Result.failure(exception);
    }


    /**
     * 系统未知异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        String active = environment.getProperty("spring.profiles.active");
        if (GlobalConstant.PROJECT_ACTIVE_PROD.equals(active)) {
            // 生产环境出现未知异常需要通知
            UnknownExecoptionNotice unknownExecoptionNotice = UnknownExecoptionNotice.of();
            try {
                // 获取服务器主机地址
                InetAddress localHost = InetAddress.getLocalHost();
                String hostAddress = localHost.getHostAddress();
                unknownExecoptionNotice.setIp(hostAddress);
            } catch (UnknownHostException e) {
                log.error("获取主机地址异常：{}", e.getMessage());
            }
            unknownExecoptionNotice.setServiceName(environment.getProperty(GlobalConstant.MODULE_NAME)).setException(exception);
            Optional.ofNullable(this.exceptionNotice).ifPresent(e -> e.notice(unknownExecoptionNotice));
        }
        return Result.failure(ErrorEnum.other_ERROR);
    }
}
