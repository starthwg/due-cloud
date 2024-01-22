package com.due.cloud.rpc.file.service.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.cloud.module.file.doamin.request.CreateFile;
import com.due.cloud.rpc.file.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class FallbackFactoryImpl implements FallbackFactory<IFileService> {
    @Override
    public IFileService create(Throwable cause) {
        log.info("异常：{}", cause.getMessage());
        log.error(ExceptionUtils.getStackTrace(cause));
        return new IFileService() {
            @Override
            public Result<Long> uploadFile(CreateFile createFile) {
                return Result.failure(ErrorEnum.SERVICE_ERROR);
            }
        };
    }
}
