package com.due.basic.setting.start;

import com.due.basic.tookit.spi.SPIServerLoad;
import com.due.basic.tookit.start.ApplicationListener;
import com.due.basic.tookit.utils.LogicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 启动基类
 */
@Slf4j
public abstract class Application {

    private static final String MODULE_NAME = "spring.application.name";

    private Class<?> aClass;


    protected List<ApplicationListener> applicationListenerList;


    /**
     * 模块名称
     */
    private String moduleName;

    protected ConfigurableApplicationContext run(String[] args) {
        this.aClass = this.getClass();
        this.moduleName = this.aClass.getSimpleName();
        this.applicationListenerList = this.getExtensionApplicationListener();
        SpringApplicationBuilder springApplicationBuilder = this.createSpringApplicationBuilder(this.moduleName, this.aClass, args);
        this.invokeApplicationListenerListBefore(springApplicationBuilder, args);
        ConfigurableApplicationContext applicationContext = springApplicationBuilder.application().run(args);
        this.invokeApplicationListenerListAfter(applicationContext, args);
        return applicationContext;
    }


    protected void invokeApplicationListenerListBefore(SpringApplicationBuilder springApplicationBuilder, String[] ages) {
        if (LogicUtil.isEmpty(this.applicationListenerList)) return;
        this.applicationListenerList.forEach(e -> e.startBefore(this.moduleName, springApplicationBuilder, ages));
    }


    protected void invokeApplicationListenerListAfter(ConfigurableApplicationContext applicationContext, String[] ages) {
        if (LogicUtil.isEmpty(this.applicationListenerList)) return;
        this.applicationListenerList.forEach(e -> e.startAfter(this.moduleName, applicationContext, ages));
    }

    // 准备获取扩展点

    private List<ApplicationListener> getExtensionApplicationListener() {
        SPIServerLoad<ApplicationListener> serverLoad = SPIServerLoad.getServerLoad(ApplicationListener.class);
        if (null == serverLoad) {
            return Collections.emptyList();
        }
        return serverLoad.getExtensionList();
    }

    private SpringApplicationBuilder createSpringApplicationBuilder(String moduleName, Class<?> runnerClass, String[] ages) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(runnerClass);
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(ages));
        propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));

        Properties properties = System.getProperties();
        properties.put(MODULE_NAME, moduleName);

        return springApplicationBuilder;
    }
}
