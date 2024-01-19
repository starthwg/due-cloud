package com.due.cloud.bridge.file.support;

import com.due.basic.tookit.utils.DateUtil;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.cloud.bridge.file.config.BridgeFileProperties;
import com.due.cloud.bridge.file.domian.DueBucketCreate;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * 抽象的文件操作类
 *
 * @param <T> 操作客户端
 * @author hanwengang
 */
@Data
public abstract class AbstractFileTemplate<T> implements FileTemplate, InitializingBean {


    /**
     * 客户端
     */
    protected T client;

    /**
     * 配置信息
     */
    @Autowired
    protected BridgeFileProperties bridgeFileProperties;

    /**
     * 创建桶
     *
     * @param dueBucket DueBucketCreate 类型
     */
    protected abstract void createBucket(DueBucketCreate dueBucket);


    /**
     * 支持的客户端类型
     *
     * @param clientEnum FileClientEnum 类型
     * @return true 支持 ，false 不支持
     */
    protected abstract boolean support(FileClientEnum clientEnum);

    /**
     * 一时间最为文件路径
     *
     * @param bucketName 需要桶的名称
     * @return string类型的文件路径 不携带文件名称
     *         例如： /due/2024-01-10/xxxx.jpg
     */
    public String getTimePath(String bucketName) {
        if (LogicUtil.isAllBlank(bucketName)) {
            bucketName = bridgeFileProperties.getBucketName();
        }

        String ymdString = DateUtil.formatDateYMD(DateUtil.getDate());
        StringBuilder builder = new StringBuilder();
        builder.append("/");
        if (LogicUtil.isAllNotNull(bucketName)) {
            builder.append(bucketName).append("/");
        }
        return builder.append(ymdString).append("/").toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(bridgeFileProperties, "文件上传配置信息不能为null");
        Assert.notNull(client, "操作客户端不能为null");
    }
}
