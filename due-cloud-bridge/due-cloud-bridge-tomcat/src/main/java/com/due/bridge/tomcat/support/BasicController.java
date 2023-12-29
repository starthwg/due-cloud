package com.due.bridge.tomcat.support;

import com.due.basic.tookit.doamin.PageSet;
import com.due.basic.tookit.utils.BeanUtil;
import com.due.basic.tookit.utils.LogicUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BasicController {

    public <T, F> F to(T source, Function<T, F> function) {
        if (null == source) return null;
        return function.apply(source);
    }

    public <F> F to(Object source, Class<F> fClass) {
        return BeanUtil.deepCopy(source, fClass);
    }

    public <T, F> PageSet<F> toPage(PageSet<T> pageSet, Function<T, F> function) {
        PageSet<F> result = new PageSet<>();
        result.setStart(pageSet.getStart());
        result.setLimit(pageSet.getLimit());
        result.setTotal(pageSet.getTotal());
        List<F> records = pageSet.getRecords().stream().map(function).collect(Collectors.toList());
        result.setRecords(records);
        return result;
    }

    public <T, F> List<F> toList(List<T> source, Function<T, F> function) {
        if (LogicUtil.isEmpty(source)) return Collections.emptyList();
        return source.stream().filter(Objects::nonNull).map(function).collect(Collectors.toList());
    }


    public <F> F copy(Object target, Class<F> rClass) {
        return BeanUtil.deepCopy(target, rClass);
    }

    public void copy(Object source, Object target) {
        BeanUtil.deepCopy(source, target);
    }

    public <T> List<T> copyList(List<?> source, Class<T> clazz) {
        if (null == source || source.isEmpty()) {
            return null;
        }
        return BeanUtil.deepCopy(source, clazz);
    }

    public <T> PageSet<T> copyPage(PageSet<?> pageSet, Class<T> clazz) {
        if (pageSet == null) return null;
        return BeanUtil.deepCopy(pageSet, clazz);
    }

}
