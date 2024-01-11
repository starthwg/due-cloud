package com.due.basic.tookit.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class LogicUtil {

    public static boolean isAllBlank(String... strings) {
        return StringUtils.isAllBlank(strings);
    }

    public static boolean isAllNotBlank(String... strings) {
        return StringUtils.isNoneBlank(strings);
    }

    public static boolean isAllNull(Object... objects) {
        return Arrays.stream(objects).allMatch(Objects::isNull);
    }

    public static boolean isAllNotNull(Object... objects) {
        return Arrays.stream(objects).allMatch(Objects::nonNull);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }


}
