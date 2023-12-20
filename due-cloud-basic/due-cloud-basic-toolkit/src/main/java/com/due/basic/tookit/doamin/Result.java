package com.due.basic.tookit.doamin;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicException;
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
@NoArgsConstructor
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


    public static Result<?> failure(LogicException e) {
        Result<?> result = new Result<>();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return result;
    }

    public static<E> Result<E> failure(ErrorEnum errorEnum) {
        Result<E> result = new Result<>();
        result.setCode(errorEnum.getCode());
        result.setMessage(errorEnum.getMessage());
        return result;
    }
}
