package com.due.cloud.service.back.integration;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.file.doamin.request.CreateFile;

/**
 * 文件相关
 */
public interface IFileIntegration {

    /**
     * 文件上传
     *
     * @param createFile CreateFile 类型
     * @return 返回文件主键
     */
    Result<Long> uploadFile(CreateFile createFile);
}
