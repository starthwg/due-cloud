package com.due.basic.tookit.yml;

import com.due.basic.tookit.utils.LogicUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    private static final String YAML_FILE = "yaml";
    private static final String YML_FILE = "yml";

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        // 获取文件名称
        String fileName = Optional.ofNullable(name).filter(e -> LogicUtil.isAllNotBlank(name)).orElseGet(() -> resource.getResource().getFilename());
        if (StringUtils.endsWith(fileName, YAML_FILE) || StringUtils.endsWith(fileName, YML_FILE)) {
            // yml 文件
            Resource resource1 = resource.getResource();
            List<PropertySource<?>> propertySourceList = new YamlPropertySourceLoader().load(fileName, resource1);
            if (LogicUtil.isEmpty(propertySourceList)) {
                throw new IOException("config file not fond " + fileName);
            } else if (propertySourceList.size() > 1) {
                throw new IOException("config file are multiple");
            } else {
                return propertySourceList.get(0);
            }
        } else {
            return fileName == null ? new ResourcePropertySource(resource) : new ResourcePropertySource(fileName, resource);
        }
    }
}
