package com.due.basic.tookit.exception;


import com.due.basic.tookit.doamin.ErrorEnum;
import com.due.basic.tookit.utils.LogicUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class LogicAssert {

    public static void isBlank(CharSequence data, ErrorEnum errorEnum, String message) {
        if (StringUtils.isAllBlank(data)) {
            throw new LogicException(errorEnum, message);
        }
    }

    public static void isBlank(CharSequence data, ErrorEnum errorEnum) {
        if (StringUtils.isAllBlank(data)) {
            throw new LogicException(errorEnum);
        }
    }

    public static void isNull(Object data, ErrorEnum errorEnum) {
        isNull(data, errorEnum, null);
    }

    public static void isNull(Object data, ErrorEnum errorEnum, String message) {
        Optional.ofNullable(data).orElseThrow(() -> new LogicException(errorEnum, message));
    }

    public static void isTrue(boolean data, ErrorEnum errorEnum, String message) {
        if (data) {
            throw new LogicException(errorEnum, message);
        }
    }

    public static void isTrue(boolean data, ErrorEnum errorEnum) {
        isTrue(data, errorEnum, null);
    }

    public static void isFalse(boolean data, ErrorEnum errorEnum, String message) {
        if (!data) {
            throw new LogicException(errorEnum, message);
        }
    }

    public static void isFalse(boolean data, ErrorEnum errorEnum) {
        isFalse(data, errorEnum, null);
    }

    public static void isEmpty(Collection<?> collection, ErrorEnum errorEnum, String message) {
        if (LogicUtil.isEmpty(collection)) {
            throw new LogicException(errorEnum, message);
        }
    }

    public static void isEmpty(Collection<?> collection, ErrorEnum errorEnum) {
        if (LogicUtil.isEmpty(collection)) {
            throw new LogicException(errorEnum, null);
        }
    }


    public static void isEmpty(Map<?, ?> collection, ErrorEnum errorEnum, String message) {
        if (LogicUtil.isEmpty(collection)) {
            throw new LogicException(errorEnum, message);
        }
    }

    public static void isEmpty(Map<?, ?> collection, ErrorEnum errorEnum) {
        if (LogicUtil.isEmpty(collection)) {
            throw new LogicException(errorEnum, null);
        }
    }
}
