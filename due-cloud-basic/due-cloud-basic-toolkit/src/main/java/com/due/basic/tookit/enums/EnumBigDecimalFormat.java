package com.due.basic.tookit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;


@Getter
@AllArgsConstructor
public enum EnumBigDecimalFormat {
    //百分比
    PERCENT(b-> Optional.of(b).map(val->val.multiply(new BigDecimal(100))).orElse(null)),
    //万元换算元
    WANYUAN(b-> Optional.of(b).map(val->val.multiply(new BigDecimal(10000))).orElse(null)),
    //百分数换算成分数
    MARK(b-> Optional.of(b).map(val->val.divide(new BigDecimal(100))).orElse(null)),
    //亿元
    BILLION(b-> Optional.of(b).map(val->val.divide(new BigDecimal(Math.pow(10,8)))).orElse(null)),
    //万元
    TEN_THOUSAND(b-> Optional.of(b).map(val->val.divide(new BigDecimal(Math.pow(10,4)))).orElse(null)),
    NONE(b->b)
    ;
    private final Function<BigDecimal, BigDecimal> desensitizer;

}
