package com.due.bridge.tomcat.aspect;

import com.alibaba.fastjson.JSONObject;
import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.doamin.DueRequest;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.rpc.DueRpcInterceptor;
import com.due.basic.tookit.utils.DateUtil;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Aspect
@Slf4j
public class RpcInterceptorAspect {

    /**
     * @ within是匹配类上的注解
     * within 是匹配类
     */
    @Pointcut("@within(com.due.basic.tookit.rpc.DueRpcInterceptor)")
    public void pointCut() {
    }


    @Around(value = "pointCut()")
    public Object execBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        addDueRequestData(joinPoint);
        Object proceed;
        Long start = System.currentTimeMillis();
        try {
            String string = Arrays.toString(joinPoint.getArgs());
            proceed = joinPoint.proceed(joinPoint.getArgs());
            log.debug("{} - end rpc [success] ages: {} ,  result : {} , time :{} millisecond", this.getTreadId(), string, JSONObject.toJSONString(proceed), System.currentTimeMillis() - start);
        } finally {
            removeRequestData();
        }
        log.debug("{} - ********************************************[rpc]********************************************", this.getTreadId());
        return proceed;
    }


    private void addDueRequestData(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DueRequest dueRequest = DueRequest.of()
                .setRpcTime(DateUtil.getDate())
                .setServiceRequestCode(this.getServiceRequestCode(method))
                .setModuleResponseCode(this.getModuleResponseCode(method))
                .setServiceScene(this.getServiceScene(method));
        // 本来就有的
        String serialNo = ThreadLocalUtil.getSerialNo();
        if (LogicUtil.isAllBlank(serialNo)) {
            serialNo = UUID.randomUUID().toString();
            ThreadLocalUtil.set(GlobalThreadLocalConstant.SERIAL_NO,serialNo);
        }
        dueRequest.setChannelEnum(ThreadLocalUtil.getChannel()).setMemberId(ThreadLocalUtil.getMemberId()).setSerialNo(serialNo);
        ThreadLocalUtil.set(GlobalConstant.DUE_RPC_MODULE_REQUEST, dueRequest);
        log.debug("{} - ********************************************[rpc]**********************************************", this.getTreadId());
        log.debug("{} - rpc module : {}  serialNo: {}", this.getTreadId(), this.getModuleResponseCode(method), ThreadLocalUtil.getSerialNo());
    }

    private ModuleServiceScene getServiceScene(Method method) {
        DueRpcInterceptor dueRpcInterceptor = method.getAnnotation(DueRpcInterceptor.class);
        if (null == dueRpcInterceptor || null == dueRpcInterceptor.scene() || ModuleServiceScene.UNKNOWN.equals(dueRpcInterceptor.scene())) {
            return Optional.ofNullable(method.getDeclaringClass().getAnnotation(DueRpcInterceptor.class))
                    .flatMap(e -> Optional.ofNullable(e.scene())).orElseThrow(() -> new LogicException(ErrorEnum.SERVICE_SCENE_UNKNOWN));
        } else {
            return dueRpcInterceptor.scene();
        }
    }

    private ModuleCodeEnum getModuleResponseCode(Method method) {
        DueRpcInterceptor dueRpcInterceptor = method.getAnnotation(DueRpcInterceptor.class);
        if (null == dueRpcInterceptor || null == dueRpcInterceptor.response() || ModuleCodeEnum.UNKNOWN.equals(dueRpcInterceptor.response())) {
            return Optional.ofNullable(method.getDeclaringClass().getAnnotation(DueRpcInterceptor.class))
                    .flatMap(e -> Optional.ofNullable(e.response())).orElseThrow(() -> new LogicException(ErrorEnum.SERVICE_RESPONSE_UNKNOWN));
        } else {
            return dueRpcInterceptor.response();
        }
    }

    private ServiceCodeEnum getServiceRequestCode(Method method) {
        DueRpcInterceptor dueRpcInterceptor = method.getAnnotation(DueRpcInterceptor.class);
        if (null == dueRpcInterceptor || null == dueRpcInterceptor.request() || ServiceCodeEnum.UNKNOWN.equals(dueRpcInterceptor.request())) {
            // 获取类上的注解
            DueRpcInterceptor classDueRpcInterceptor = method.getDeclaringClass().getAnnotation(DueRpcInterceptor.class);
            return Optional.ofNullable(classDueRpcInterceptor).map(DueRpcInterceptor::request).orElseThrow(() -> new LogicException(ErrorEnum.SERVICE_REQUEST_UNKNOWN));
        } else {
            return dueRpcInterceptor.request();
        }
    }


    private void removeRequestData() {
        ThreadLocalUtil.removeDueRequest(GlobalConstant.DUE_RPC_MODULE_REQUEST);
    }

    private Long getTreadId() {
        return Thread.currentThread().getId();
    }

    private String getThreadName() {
        return Thread.currentThread().getName();
    }
}
