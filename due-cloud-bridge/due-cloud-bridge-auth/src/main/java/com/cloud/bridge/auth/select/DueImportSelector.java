package com.cloud.bridge.auth.select;

import com.due.basic.tookit.utils.LogicUtil;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * 项目类导入器
 *
 * @author hanwengang
 */
public abstract class DueImportSelector implements ImportSelector, BeanClassLoaderAware {


    private ClassLoader classLoader;

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> loadedFactories = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.classLoader);
        if (LogicUtil.isEmpty(loadedFactories)) {
            return new String[0];
        }
        return loadedFactories.toArray(new String[0]);
    }


    /**
     * 获取工厂加载类的class
     *
     * @return class对象
     */
    protected abstract Class<?> getSpringFactoriesLoaderFactoryClass();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
