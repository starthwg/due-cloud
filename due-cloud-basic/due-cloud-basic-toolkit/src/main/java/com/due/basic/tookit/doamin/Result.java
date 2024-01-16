package com.due.basic.tookit.doamin;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.function.DueProducer;
import com.due.basic.tookit.utils.LogicUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;

/**
 * 通用的接口返回类型
 *
 * @param <T>
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@Slf4j
public class Result<T> implements Serializable {

    /**
     * 编码
     */
    private Integer code = ErrorEnum.SUCCESS.getCode();

    /**
     * 消息
     */
    private String message = ErrorEnum.SUCCESS.getMessage();


    /**
     * 返回的数据
     */
    private T data;

    public Result() {
    }

    public Result(ErrorEnum errorEnum) {
        if (null != errorEnum) {
            this.code = errorEnum.getCode();
            this.message = errorEnum.getMessage();
        }
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ErrorEnum.SUCCESS.getCode().equals(this.code);
    }

    @JsonIgnore
    public boolean isFailure() {
        return this.isSuccess() == false;
    }

    public void whenFailureThenThrowException() {
        LogicAssert.isTrue(this.isFailure(), ErrorEnum.parseByCode(this.code), this.message);
    }

    public void whenFailureThenThrowException(String desc) {
        LogicAssert.isTrue(this.isFailure(), ErrorEnum.parseByCode(this.code), desc);
    }

    public static Result<?> failure(LogicException e) {
        Result<?> result = new Result<>();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return result;
    }

    public static <E> Result<E> failure(ErrorEnum errorEnum) {
        Result<E> result = new Result<>();
        result.setCode(errorEnum.getCode());
        result.setMessage(errorEnum.getMessage());
        return result;
    }

    public static <E> Result<E> failure(ErrorEnum errorEnum, String message) {
        Result<E> result = new Result<>();
        result.setCode(errorEnum.getCode());
        result.setMessage(errorEnum.getMessage());
        if (LogicUtil.isAllNotBlank(message)) {
            result.setMessage(result.message + ":" + message);
        }
        return result;
    }

    public static <E> Result<E> success(E object) {
        Result<E> objectResult = new Result<>();
        objectResult.setMessage(ErrorEnum.SUCCESS.getMessage());
        objectResult.setCode(ErrorEnum.SUCCESS.getCode());
        objectResult.setData(object);
        return objectResult;
    }

    public static <E> Result<E> success() {
        Result<E> objectResult = new Result<>();
        objectResult.setMessage(ErrorEnum.SUCCESS.getMessage());
        objectResult.setCode(ErrorEnum.SUCCESS.getCode());
        return objectResult;
    }

    public static <E> Result<E> exec(DueProducer<E> dueProducer) {
        if (null == dueProducer) {
            return Result.success();
        }
        E apply = null;
        try {
            apply = dueProducer.apply();
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new LogicException(ErrorEnum.SERVICE_ERROR, e.getMessage());
        }
        return success(apply);
    }
}
