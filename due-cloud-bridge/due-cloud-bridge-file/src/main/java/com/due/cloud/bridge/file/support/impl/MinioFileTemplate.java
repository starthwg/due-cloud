package com.due.cloud.bridge.file.support.impl;

import com.due.cloud.bridge.file.domian.DueBucketCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectResult;
import com.due.cloud.bridge.file.domian.DuePutObjectCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectResult;
import com.due.cloud.bridge.file.support.AbstractFileTemplate;
import com.due.cloud.bridge.file.support.FileClientEnum;
import io.minio.MinioClient;

/**
 * MinIo客户端操作
 *
 * @author hanwengang
 */
public class MinioFileTemplate extends AbstractFileTemplate<MinioClient> {


    @Override
    protected void createBucket(DueBucketCreate dueBucket) {

    }

    @Override
    protected boolean support(FileClientEnum clientEnum) {
        return FileClientEnum.MIN_IO.equals(clientEnum);
    }

    @Override
    public DuePutObjectResult putObject(DuePutObjectCreate putObjectCreate) {
        return null;
    }

    @Override
    public DueGetObjectResult getObject(DueGetObjectCreate getObjectCreate) {
        return null;
    }
}
