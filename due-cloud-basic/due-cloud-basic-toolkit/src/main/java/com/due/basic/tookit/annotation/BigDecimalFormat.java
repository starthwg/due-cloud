package com.due.basic.tookit.annotation;


import com.due.basic.tookit.enums.EnumBigDecimalFormat;
import com.due.basic.tookit.serializer.BigDecimalSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = BigDecimalSerializer.class)
public @interface BigDecimalFormat {
    //转换格式
    EnumBigDecimalFormat format() default EnumBigDecimalFormat.NONE;

    //保留小数位
    int digits() default 0;

    //舍进方式
    int roundingMode() default BigDecimal.ROUND_HALF_UP;
}
