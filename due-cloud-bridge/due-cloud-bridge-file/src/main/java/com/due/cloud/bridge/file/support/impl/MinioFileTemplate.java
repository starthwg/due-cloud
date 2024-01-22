package com.due.cloud.bridge.file.support.impl;

import com.alibaba.fastjson.JSONObject;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.cloud.bridge.file.domian.DueBucketCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectResult;
import com.due.cloud.bridge.file.domian.DuePutObjectCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectResult;
import com.due.cloud.bridge.file.support.AbstractFileTemplate;
import com.due.cloud.bridge.file.support.FileClientEnum;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * MinIo客户端操作
 *
 * @author hanwengang
 */
@Slf4j
public class MinioFileTemplate extends AbstractFileTemplate<MinioClient> {


    @Override
    protected void createBucket(DueBucketCreate dueBucket) {
        String bucketName = Optional.ofNullable(dueBucket).flatMap(e -> Optional.ofNullable(e.getBucketName()))
                .orElseGet(() -> this.bridgeFileProperties.getBucketName());
        try {
            this.client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).objectLock(false).build());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new LogicException(ErrorEnum.DATA_HANDLE_ERROR, "文件上传异常");
        }
    }

    @Override
    protected boolean support(FileClientEnum clientEnum) {
        return FileClientEnum.MIN_IO.equals(clientEnum);
    }

    @Override
    protected void uploadFile(DuePutObjectCreate putObjectCreate) throws IOException {
        try {
            putObjectCreate.setClientEnum(FileClientEnum.MIN_IO);
            ObjectWriteResponse objectWriteResponse = this.client.putObject(PutObjectArgs.builder().contentType("application/octet-stream").stream(putObjectCreate.getInputStream(), putObjectCreate.getInputStream().available(), -1).bucket(putObjectCreate.getBucketName()).object(putObjectCreate.getNewFileName()).build());
            log.info("文件上传结果：{}", JSONObject.toJSONString(objectWriteResponse));
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new LogicException(ErrorEnum.DATA_HANDLE_ERROR, "文件上传失败！");
        }
    }

    @Override
    protected boolean existBucket(String bucketName) {
        try {
            return this.client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new LogicException(ErrorEnum.SERVICE_ERROR, "文件上传失败！");
        }
    }

    @Override
    public DueGetObjectResult getObject(DueGetObjectCreate getObjectCreate) {
        return null;
    }
}
