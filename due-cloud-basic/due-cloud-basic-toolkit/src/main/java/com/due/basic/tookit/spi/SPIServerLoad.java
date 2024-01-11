package com.due.basic.tookit.spi;

import com.due.basic.tookit.utils.LogicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * SPI机制的加载类
 *
 * @author hanwengang
 */
@Slf4j
public class SPIServerLoad<T> {

    /**
     * 加载SPI的地址
     */
    private static final String D_SERVICE = "META-INF/service";
    private static final String D_DUE = "META-INF/due";


    private static final int DEFAULT_ORDER = Integer.MAX_VALUE - 100;

    /**
     *
     */
    private static final Map<Class<?>, SPIServerLoad<?>> SPI_SERVERLOAD_MAP = new ConcurrentHashMap<>(8);

    private static final Set<String> SPI_PATH = new HashSet<>();

    static {
        SPI_PATH.add(D_SERVICE);
        SPI_PATH.add(D_DUE);
    }

    /**
     * 需要加载的类
     */
    private Class<T> tClass;

    /**
     * 扩展名称
     */
    private String name;


    /**
     * 是否是单例
     */
    private SPIScope scope;

    /**
     * 保存当前扩展类的实现类
     */
    private final Map<String, T> BEAN_MAPPING = new ConcurrentHashMap<>(10);

    private final ConcurrentMap<String, Class<T>> TYPE_MAPPING = new ConcurrentHashMap<String, Class<T>>();


    private SPIServerLoad(Class<T> tClass) {
        if (null == tClass) {
            throw new IllegalArgumentException("SPI type must not be null");
        }
        if (!tClass.isInterface()) {
            throw new IllegalArgumentException("SPI type must not be Interface");
        }
        this.tClass = tClass;
        // 解析注解信息
        SPI spi = this.tClass.getAnnotation(SPI.class);
        if (null == spi) {
            throw new IllegalArgumentException("SPi must have Annotation " + SPI.class.getName());
        }
        this.scope = spi.scope();
        this.name = spi.value();
        this.refresh();
    }


    private void refresh() {
        for (String path : SPI_PATH) {
            path = String.format("%s/%s", path, this.tClass.getName());
            log.info("SPI：{},加载的地址为：{}", tClass, path);
            Enumeration<URL> urls = null;
            try {
                ClassLoader classLoader = SPIServerLoad.class.getClassLoader();
                if (null == classLoader) {
                    urls = ClassLoader.getSystemResources(path);
                } else {
                    urls = classLoader.getResources(path);
                }
                if (null == urls) {
                    return;
                }
                while (urls.hasMoreElements()) {
                    // 获取一个资源
                    URL url = urls.nextElement();
                    try (InputStream inputStream = url.openStream()) {
                        // 将资源转化成map
                        Properties properties = new Properties();
                        properties.load(inputStream);
                        for (Object keyObj : properties.keySet()) {
                            String key = Optional.ofNullable(keyObj).map(Object::toString).orElse(null);
                            if (LogicUtil.isAllBlank(key)) return;
                            String value = Optional.ofNullable(properties.get(key)).map(Object::toString).orElse(key);
                            log.info("类的权限的类名:{}", value);
                            Class<T> aClass = parse(Class.forName(value));
                            SPIName annotation = aClass.getAnnotation(SPIName.class);
                            // 获取扩展点的名称
                            String spiName = Optional.ofNullable(annotation).map(SPIName::name).filter(LogicUtil::isAllNotBlank).orElse(key);
                            TYPE_MAPPING.put(spiName, aClass);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    public List<T> getExtensionList() {
        if (LogicUtil.isEmpty(TYPE_MAPPING)) {
            return Collections.emptyList();
        }
        return this.TYPE_MAPPING.keySet().stream().map(this::getExtension).filter(LogicUtil::isAllNotNull).sorted(((o1, o2) -> {
            Integer order1 = Optional.ofNullable(o1).flatMap(e -> Optional.of(e.getClass())).map(e -> e.getAnnotation(SPIOrder.class)).map(SPIOrder::value).orElse(DEFAULT_ORDER);
            Integer order2 = Optional.ofNullable(o2).flatMap(e -> Optional.of(e.getClass())).map(e -> e.getAnnotation(SPIOrder.class)).map(SPIOrder::value).orElse(DEFAULT_ORDER);
            return order1 - order2;
        })).collect(Collectors.toList());
    }

    private T getExtension(String name) {
        try {
            if (LogicUtil.isAllBlank(name)) {
                name = this.name;
            }
            Class<T> aClass = TYPE_MAPPING.get(name);
            if (null == aClass) {
                return null;
            }
            // 多利模式直接构建一个对象返回
            if (SPIScope.Prototype.equals(this.scope)) {
                return aClass.newInstance();
            }
            return this.BEAN_MAPPING.computeIfAbsent(name, nameKey -> {
                try {
                    return aClass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T parse(Object obj) {
        return (T) obj;
    }


    public static <T> SPIServerLoad<T> getServerLoad(Class<T> classType) {
        SPIServerLoad<T> spiServerLoad = parse(SPI_SERVERLOAD_MAP.get(classType));
        if (null == spiServerLoad) {
            // 如果不存在就构建一个
            spiServerLoad = new SPIServerLoad<>(classType);
            SPI_SERVERLOAD_MAP.put(classType, spiServerLoad);
        }
        return spiServerLoad;
    }

}
