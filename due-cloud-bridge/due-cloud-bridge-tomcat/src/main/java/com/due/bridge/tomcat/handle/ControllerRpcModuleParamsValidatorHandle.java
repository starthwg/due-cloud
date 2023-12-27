package com.due.bridge.tomcat.handle;

import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.doamin.DueRequest;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.basic.tookit.utils.ThreadContextStoreUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Order(value = Integer.MIN_VALUE + 999)
public class ControllerRpcModuleParamsValidatorHandle implements HandlerInterceptor {

    private Validator validator;

    public ControllerRpcModuleParamsValidatorHandle() {
    }

    public ControllerRpcModuleParamsValidatorHandle(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        // 说明是最外层服务执行，直接放行
        if (LogicUtil.isAllNotBlank(url) && url.startsWith(GlobalConstant.PROJECT_BASE_PATH)) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Object object = ThreadContextStoreUtil.getInstance().get(GlobalConstant.DUE_RPC_MODULE_REQUEST);
        if (object instanceof DueRequest) {
            DueRequest dueRequest = (DueRequest) object;
            Set<ConstraintViolation<DueRequest>> validateSet = validator.validate(dueRequest);
            if (LogicUtil.isNotEmpty(validateSet)) {
                StringBuilder builder = new StringBuilder();
                for (ConstraintViolation<DueRequest> violation : validateSet) {
                    builder.append(violation.getMessage()).append(",");
                }
                String message = builder.substring(0, builder.length() - 1);
                throw new LogicException(ErrorEnum.PARAMETER_ERROR, message);
            }
        } else {
            throw new LogicException(ErrorEnum.SERVICE_REQUEST_UNKNOWN);
        }
        return true;
    }
}
