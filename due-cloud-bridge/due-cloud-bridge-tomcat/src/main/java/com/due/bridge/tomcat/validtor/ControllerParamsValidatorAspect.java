package com.due.bridge.tomcat.validtor;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.utils.LogicUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * controller层的参数校验
 */
@Aspect
@Slf4j
@Order(value = Integer.MIN_VALUE + 100)
public class ControllerParamsValidatorAspect {


    @Autowired
    private Validator validator;

    @Autowired
    private ApplicationContext applicationContext;


    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void pointCut() {

    }

    @Before(value = "pointCut()")
    public void execControllerBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        ExecutableValidator executableValidator = validator.forExecutables();
        Object bean = applicationContext.getBean(method.getDeclaringClass());
        Set<ConstraintViolation<Object>> constraintViolations = executableValidator.validateParameters(bean, method, joinPoint.getArgs());
        if (null == constraintViolations) {
            constraintViolations = new HashSet<>();
        }

        for (Object arg : args) {
            if (null == arg) {
                continue;
            }
            Set<ConstraintViolation<Object>> validate = validator.validate(arg);
            constraintViolations.addAll(validate);
        }
        if (LogicUtil.isEmpty(constraintViolations)) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            builder.append(message).append(",");
        }
        String message = builder.substring(0, builder.length() - 1);
        throw new LogicException(ErrorEnum.PARAMETER_ERROR, message);
    }
}
