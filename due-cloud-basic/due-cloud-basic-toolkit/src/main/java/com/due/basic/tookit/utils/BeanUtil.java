package com.due.basic.tookit.utils;

import com.due.basic.tookit.doamin.PageSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("unchecked")
public class BeanUtil {
    public static <T> T deepCopy(Object source, Class<T> clazz) {
        if (source == null || clazz == null) return null;
        return OrikaMapperUtil.map(source, clazz);
    }

    public static void deepCopy(Object source, Object clazz) {
        if (source == null || clazz == null) return;
        OrikaMapperUtil.map(source, clazz);
    }

    public static <T> List<T> copy(List<?> list, Class<T> clazz) {
        if (list == null || list.isEmpty()) return null;
        return list.stream().map(v -> deepCopy(v, clazz)).collect(Collectors.toList());
    }

    public static <T> List<T> deepCopy(List<?> list, Class<T> clazz) {
        if (list == null || list.isEmpty()) return null;
        return OrikaMapperUtil.mapList(list, clazz);
    }

    public static <T> PageSet<T> copy(PageSet<?> page, Class<T> clazz) {
        if (page == null) return null;
        PageSet<T> pageSet = new PageSet<>(page.getStart(), page.getLimit());
        pageSet.setTotal(page.getTotal());
        pageSet.setRecords(copy(page.getRecords(), clazz));
        return pageSet;
    }

    public static <T> PageSet<T> deepCopy(PageSet<?> page, Class<T> clazz) {
        if (page == null) return null;
        PageSet<T> pageSet = new PageSet<>(page.getStart(), page.getLimit());
        pageSet.setTotal(page.getTotal());
        pageSet.setRecords(deepCopy(page.getRecords(), clazz));
        return pageSet;
    }

    public static <T> void partialCopy(Object source, Object target, Object... keys) {
        if (keys == null || keys.length == 0) return;
        BeanMap t1 = BeanMap.create(source);
        BeanMap t2 = BeanMap.create(target);
        int i = keys.length;
        while (i-- > 0) {
            t2.replace(keys[i], t1.get(keys[i]));
        }
    }


    public static <T> void filterCopy(Object source, Object target, BiPredicate<String, Object> action) {
        final BeanWrapper bw = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = bw.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object value = pd.getValue(pd.getName());
            if (action.test(pd.getName(), value)) emptyNames.add(pd.getName());
        }
        partialCopy(source, target, emptyNames.toArray());
    }

    private static String genKey(Class<?> clz1, Class<?> clz2) {
        return clz1.getName() + clz2.getName();
    }

    /**
     * 清空集合数据
     *
     * @param collections
     */
    public static void emptyMe(Collection<?>... collections) {
        if (collections != null && collections.length > 0) {
            for (Collection<?> e : collections) {
                if (e != null) {
                    e.clear();
                }
            }
        }
    }

    /**
     * 清空Map集合数据
     *
     * @param maps
     */
    public static void emptyMe(Map<?, ?>... maps) {
        if (maps != null && maps.length > 0) {
            for (Map<?, ?> e : maps) {
                if (e != null) {
                    e.clear();
                }
            }
        }
    }

    /**
     * 清空集合数据，并将集合对象置为null
     *
     * @param collections
     */
    public static void nullMe(Collection<?>... collections) {
        if (collections != null && collections.length > 0) {
            for (Collection<?> e : collections) {
                if (e != null) {
                    e.clear();
                    e = null;
                }
            }
            collections = null;
        }
    }

    /**
     * 清空Map对象，并将Map对象置为null
     *
     * @param maps
     */
    public static void nullMe(Map<?, ?>... maps) {
        if (maps != null && maps.length > 0) {
            for (Map<?, ?> e : maps) {
                if (e != null) {
                    e.clear();
                    e = null;
                }
            }
            maps = null;
        }
    }


    public static <T> Map<?, ?> bean2Map(T source) {
        return BeanMap.create(source);
    }
}