package com.due.cloud.service.back.integration;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.file.doamin.request.CreateFileRecord;
import com.due.cloud.module.file.doamin.response.FileRecord;
import com.due.cloud.module.file.doamin.response.FileRecordData;

/**
 * 文件相关
 */
public interface IFileIntegration {

    /**
     * 文件上传
     *
     * @param createFileRecord CreateFileRecord 类型
     * @return 返回文件主键
     */
    Result<Long> uploadFile(CreateFileRecord createFileRecord);

    Result<FileRecord> getFileRecordByDataId(Long dataId);

    Result<FileRecordData> getFileRecordAndDataByDataId(Long dataId);
}
