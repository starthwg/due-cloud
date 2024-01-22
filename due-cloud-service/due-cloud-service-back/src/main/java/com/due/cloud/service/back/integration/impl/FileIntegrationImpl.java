package com.due.cloud.service.back.integration.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import com.due.basic.tookit.rpc.DueRpcInterceptor;
import com.due.basic.tookit.rpc.DueRpcService;
import com.due.cloud.module.file.doamin.request.CreateFileRecord;
import com.due.cloud.module.file.doamin.response.FileRecord;
import com.due.cloud.module.file.doamin.response.FileRecordData;
import com.due.cloud.rpc.file.service.IFileService;
import com.due.cloud.service.back.integration.IFileIntegration;

import javax.annotation.Resource;

@DueRpcService
@DueRpcInterceptor(request = ServiceCodeEnum.BACK, response = ModuleCodeEnum.FILE)
public class FileIntegrationImpl implements IFileIntegration {


    @Resource
    private IFileService fileService;

    @DueRpcInterceptor(scene = ModuleServiceScene.INSERT)
    @Override
    public Result<Long> uploadFile(CreateFileRecord createFileRecord) {
        return fileService.uploadFile(createFileRecord);
    }

    @DueRpcInterceptor(scene = ModuleServiceScene.SELECT)
    @Override
    public Result<FileRecord> getFileRecordByDataId(Long dataId) {
        return fileService.getFileDataByDataId(dataId);
    }

    @DueRpcInterceptor(scene = ModuleServiceScene.SELECT)
    @Override
    public Result<FileRecordData> getFileRecordAndDataByDataId(Long dataId) {
        return fileService.getFileDataAndDataByDataId(dataId);
    }
}
