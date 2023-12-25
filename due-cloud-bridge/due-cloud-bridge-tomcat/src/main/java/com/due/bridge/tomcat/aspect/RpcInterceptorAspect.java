package com.due.bridge.tomcat.aspect;

import com.due.basic.tookit.doamin.DueRequest;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.rpc.RpcInterceptor;
import com.due.basic.tookit.utils.DateUtil;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Slf4j
public class RpcInterceptorAspect {

    /**
     * @ within是匹配类上的注解
     * within 是匹配类
     */
    @Pointcut("@within(com.due.basic.tookit.rpc.RpcInterceptor)")
    public void pointCut() {
    }


    @Around(value = "pointCut()")
    public Object execBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        addDueRequestData(joinPoint);
        Object proceed;
        try {
            proceed = joinPoint.proceed(joinPoint.getArgs());
        } finally {
            removeRequestData();
        }

        return proceed;
    }


    private void addDueRequestData(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DueRequest dueRequest = DueRequest.of()
                .setRpcTime(DateUtil.getDate())
                .setModuleRequestCode(this.getModuleRequestCode(method))
                .setModuleResponseCode(this.getModuleResponseCode(method))
                .setServiceScene(this.getServiceScene(method));
        // 本来就有的
        dueRequest.setChannelEnum(ThreadLocalUtil.getChannel()).setMemberId(ThreadLocalUtil.getMemberId()).setSerialNo(ThreadLocalUtil.getSerialNo());
        ThreadLocalUtil.setDueRequest(dueRequest);
    }

    private ModuleServiceScene getServiceScene(Method method) {
        RpcInterceptor rpcInterceptor = method.getAnnotation(RpcInterceptor.class);
        if (null == rpcInterceptor || null == rpcInterceptor.scene() || ModuleServiceScene.UNKNOWN.equals(rpcInterceptor.scene())) {
            return Optional.ofNullable(method.getDeclaringClass().getAnnotation(RpcInterceptor.class))
                    .flatMap(e -> Optional.ofNullable(e.scene())).orElseThrow(() -> new LogicException(ErrorEnum.SERVICE_SCENE_UNKNOWN));
        } else {
            return rpcInterceptor.scene();
        }
    }

    private ModuleCodeEnum getModuleResponseCode(Method method) {
        RpcInterceptor rpcInterceptor = method.getAnnotation(RpcInterceptor.class);
        if (null == rpcInterceptor || null == rpcInterceptor.response() || ModuleCodeEnum.unknown.equals(rpcInterceptor.response())) {
            return Optional.ofNullable(method.getDeclaringClass().getAnnotation(RpcInterceptor.class))
                    .flatMap(e -> Optional.ofNullable(e.response())).orElseThrow(() -> new LogicException(ErrorEnum.SERVICE_RESPONSE_UNKNOWN));
        } else {
            return rpcInterceptor.response();
        }
    }

    private ModuleCodeEnum getModuleRequestCode(Method method) {
        RpcInterceptor rpcInterceptor = method.getAnnotation(RpcInterceptor.class);
        if (null == rpcInterceptor || null == rpcInterceptor.request() || ModuleCodeEnum.unknown.equals(rpcInterceptor.request())) {
            // 获取类上的注解
            RpcInterceptor classRpcInterceptor = method.getDeclaringClass().getAnnotation(RpcInterceptor.class);
            return Optional.ofNullable(classRpcInterceptor).map(RpcInterceptor::request).orElseThrow(() -> new LogicException(ErrorEnum.SERVICE_REQUEST_UNKNOWN));
        } else {
            return rpcInterceptor.request();
        }
    }


    private void removeRequestData() {
        ThreadLocalUtil.removeDueRequestRpc();
    }
}
