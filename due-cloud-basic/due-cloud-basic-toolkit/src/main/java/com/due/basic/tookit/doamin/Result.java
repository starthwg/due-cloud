package com.due.basic.tookit.doamin;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Result<T> implements Serializable {

    /**
     * 编码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;


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

    public static Result<?> success(Object object) {
        Result<Object> objectResult = new Result<>();
        objectResult.setMessage(ErrorEnum.SUCCESS.getMessage());
        objectResult.setCode(ErrorEnum.SUCCESS.getCode());
        objectResult.setData(object);
        return objectResult;
    }

    public static Result<?> success() {
        Result<Object> objectResult = new Result<>();
        objectResult.setMessage(ErrorEnum.SUCCESS.getMessage());
        objectResult.setCode(ErrorEnum.SUCCESS.getCode());
        return objectResult;
    }
}
