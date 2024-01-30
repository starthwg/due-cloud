package com.due.basic.tookit.function;

import java.math.BigDecimal;


/**
 * BigDecimal函数式接口
 * @param <T>
 */
@FunctionalInterface
public interface ToBigDecimalFunction<T> {
    BigDecimal applyAsBigDecimal(T value);
}
