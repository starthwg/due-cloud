package com.due.bridge.tomcat.config;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.basic.tookit.utils.ThreadContextStoreUtil;
import com.due.bridge.tomcat.aspect.ControllerParamsValidatorAspect;
import com.due.bridge.tomcat.aspect.RpcInterceptorAspect;
import com.due.bridge.tomcat.handle.ControllerDueHandle;
import com.due.bridge.tomcat.handle.ControllerErrorHandle;
import com.due.bridge.tomcat.handle.ControllerLoggerHandle;
import com.due.bridge.tomcat.handle.ControllerRpcHeaderConvertLocalHandle;
import com.due.bridge.tomcat.handle.ControllerRpcModuleParamsValidatorHandle;
import com.due.bridge.tomcat.support.impl.ExceptionNoticeComposite;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskDecorator;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;


@Slf4j
@SpringBootConfiguration
@EnableConfigurationProperties(value = {BridgeTomcatProperties.class})
@EnableAsync
public class BridgeTomcatConfig implements WebMvcConfigurer {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BridgeTomcatProperties bridgeTomcatProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        this.getHandlerInterceptorList()
                .forEach(e -> registry.addInterceptor(e).order(this.getOrder(e)));
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        WebMvcConfigurer.super.addReturnValueHandlers(handlers);
    }

    @Bean
    public ControllerErrorHandle controllerErrorHandle() {
        return new ControllerErrorHandle();
    }

    /**
     * controller层的参数校验器
     */
    @Bean
    public ControllerParamsValidatorAspect controllerParamsValidatorAspect() {
        return new ControllerParamsValidatorAspect();
    }

    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
    }

    @Bean
    public ExceptionNoticeComposite exceptionNoticeComposite() {
        return new ExceptionNoticeComposite();
    }

    @Bean
    public RpcInterceptorAspect rpcInterceptorAspect() {
        return new RpcInterceptorAspect();
    }

    @Primary
    @Bean
    public Executor executor() {
        BridgeTomcatProperties.ExecutorProperties executorProperties = this.bridgeTomcatProperties.getExecutorProperties();
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(executorProperties.getPoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(executorProperties.getMaxSize());
        threadPoolTaskExecutor.setQueueCapacity(executorProperties.getCapacity());
        threadPoolTaskExecutor.setThreadNamePrefix(executorProperties.getPrefix());
        // 设置线程的装饰器讲父线程的threadLocal的值给子线程
        threadPoolTaskExecutor.setTaskDecorator(taskDecorator());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }


    private TaskDecorator taskDecorator() {
        return runnable -> {
            Map<String, Object> map = ThreadContextStoreUtil.getInstance().getThreadContext().get();
            return () -> {
                try {
                    ThreadContextStoreUtil.getInstance().getThreadContext().set(map);
                    runnable.run();
                } finally {
                    ThreadContextStoreUtil.getInstance().clear();
                }
            };
        };
    }

    private List<HandlerInterceptor> getHandlerInterceptorList() {
        List<HandlerInterceptor> handlerInterceptorList = new ArrayList<>();
        // controller日志
        handlerInterceptorList.add(new ControllerLoggerHandle());

        // 设置公共参数
        handlerInterceptorList.add(new ControllerDueHandle());

        // openFeign的header转化成threadLocal
        handlerInterceptorList.add(new ControllerRpcHeaderConvertLocalHandle());

        // openFeign的调用参数校验
        handlerInterceptorList.add(new ControllerRpcModuleParamsValidatorHandle(this.validator()));

        return handlerInterceptorList;
    }

    private int getOrder(HandlerInterceptor interceptor) {
        if (null == interceptor) {
            return Integer.MAX_VALUE;
        }
        Order annotation = interceptor.getClass().getAnnotation(Order.class);
        int result = Integer.MAX_VALUE;
        if (null != annotation) {
            result = annotation.value();
        }
        return result;
    }
}
