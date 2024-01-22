package com.due.cloud.rpc.file.service;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.file.doamin.request.CreateFile;
import com.due.cloud.rpc.file.service.impl.FallbackFactoryImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 文件模块的api
 */
@FeignClient(value = "ModuleFile", fallbackFactory = FallbackFactoryImpl.class)
public interface IFileService {


    /**
     * 服务端文件上传
     *
     * @param createFile createFile类型
     * @return 文件主键ID
     */
    @PostMapping("/file/file/record/upload")
    Result<Long> uploadFile(@RequestBody CreateFile createFile);
}
