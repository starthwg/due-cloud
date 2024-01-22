package com.due.cloud.bridge.file.support;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.utils.DateUtil;
import com.due.basic.tookit.utils.FileUtil;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import com.due.cloud.bridge.file.config.BridgeFileProperties;
import com.due.cloud.bridge.file.domian.DueBucketCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * 抽象的文件操作类
 *
 * @param <T> 操作客户端
 * @author hanwengang
 */
@Slf4j
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

    @Override
    public DuePutObjectResult putObject(DuePutObjectCreate putObjectCreate) throws IOException {
        LogicAssert.isNull(putObjectCreate, ErrorEnum.PARAMETER_ERROR);
        String bucketName = Optional.ofNullable(putObjectCreate.getBucketName()).orElseGet(() -> this.bridgeFileProperties.getBucketName());
        if (!this.existBucket(bucketName)) {
            DueBucketCreate dueBucketCreate = new DueBucketCreate();
            dueBucketCreate.setBucketName(bucketName);
            this.createBucket(dueBucketCreate);
        }
        ServiceCodeEnum requestService = ThreadLocalUtil.getRequestService();
        String desc = requestService.getDesc();
        // 处理文件的上传路径
        String timePath = this.getTimePath();
        String newFileName = FileUtil.getFileName() + "." + FileUtil.fileType(putObjectCreate.getFileName());
        String filePath = desc + timePath + newFileName;
        putObjectCreate.setNewFileName(filePath);
        putObjectCreate.setBucketName(bucketName);
        putObjectCreate.setInputStream(new ByteArrayInputStream(putObjectCreate.getFileData()));
        this.uploadFile(putObjectCreate);

        DuePutObjectResult result = new DuePutObjectResult();
        result.setFilePath(filePath);
        result.setFileType(FileUtil.fileType(putObjectCreate.getFileName()));
        result.setBucketName(bucketName);
        result.setNewFileName(newFileName);
        result.setClientEnum(putObjectCreate.getClientEnum());
        return result;
    }

    /**
     * 上传文件
     */
    protected abstract void uploadFile(DuePutObjectCreate putObjectCreate) throws IOException;

    /**
     * 创建桶
     *
     * @param dueBucket DueBucketCreate 类型
     */
    protected abstract void createBucket(DueBucketCreate dueBucket);


    /**
     * 是否存在当前buket
     *
     * @param bucketName String类型
     * @return true 不存在， false存在
     */
    protected abstract boolean existBucket(String bucketName);

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
     * @return string类型的文件路径 不携带文件名称
     * 例如：
     */
    public String getTimePath() {
        String ymdString = DateUtil.formatDateYMD(DateUtil.getDate());
        return "/" +
                ymdString + "/";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(bridgeFileProperties, "文件上传配置信息不能为null");
        Assert.notNull(client, "操作客户端不能为null");
    }
}
