package com.due.cloud.bridge.file.support;

import com.due.cloud.bridge.file.domian.DueBucketCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectResult;
import com.due.cloud.bridge.file.domian.DuePutObjectCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectResult;

import java.io.IOException;

/**
 * 操作文件的模板
 *
 * @author hanwengang
 */
public interface FileTemplate {


    /**
     * 文件上传
     *
     * @param putObjectCreate DuePutObjectCreate类型
     * @return DuePutObjectResult 类型
     */
    DuePutObjectResult putObject(DuePutObjectCreate putObjectCreate) throws IOException;





    /**
     *  获取文件
     * @param getObjectCreate DueGetObjectCreate类型
     * @return DueGetObjectResult 类型
     */
    DueGetObjectResult getObject(DueGetObjectCreate getObjectCreate);

}
