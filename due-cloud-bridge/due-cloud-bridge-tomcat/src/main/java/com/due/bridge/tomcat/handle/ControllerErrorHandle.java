package com.due.bridge.tomcat.handle;

import com.alibaba.fastjson.JSONObject;
import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicException;
import com.due.bridge.tomcat.config.BridgeTomcatProperties;
import com.due.bridge.tomcat.support.ExceptionNotice;
import com.due.bridge.tomcat.support.UnknownExecoptionNotice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Set;

/**
 * 处理controller异常的
 */
@Slf4j
@RestControllerAdvice
public class ControllerErrorHandle /*implements ResponseBodyAdvice<Object>*/ {


    @Autowired
    private Environment environment;


    /**
     * 处理未知异常通知
     */
    @Autowired
    private ExceptionNotice exceptionNotice;

    @Autowired
    private BridgeTomcatProperties bridgeTomcatProperties;


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

//    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

//    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("准备包装返回结果");
        if (this.isMatch(request) || body instanceof Result) {
            return body;
        }
        if (null == body) return Result.success();
        Result<?> success = Result.success(body);
        if (body instanceof String) {
            return JSONObject.toJSONString(success);
        }
        return Result.success(body);
    }

    public boolean isMatch(ServerHttpRequest request) {
        Set<String> ignores = this.bridgeTomcatProperties.getResponseIgnoreProperties().getUrl();
        if (null == ignores) return false;
        String contextPath = ((ServletServerHttpRequest) request).getServletRequest().getContextPath();
        String[] ignoreUrls = ignores.stream().map(e -> StringUtils.join(contextPath, e)).toArray(String[]::new);
        String requestUri = request.getURI().getPath();
        boolean matched = PatternMatchUtils.simpleMatch(ignoreUrls, requestUri);
        log.info("直接返回数据的请求：{}，匹配结果：{}", requestUri, matched);
        return matched;
    }
}
