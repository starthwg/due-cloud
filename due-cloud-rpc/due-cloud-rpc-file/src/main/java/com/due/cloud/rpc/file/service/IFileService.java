package com.due.cloud.rpc.file.service;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.file.doamin.request.CreateFileRecord;
import com.due.cloud.module.file.doamin.response.FileRecord;
import com.due.cloud.module.file.doamin.response.FileRecordData;
import com.due.cloud.rpc.file.service.impl.FallbackFactoryImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * @param createFileRecord createFile类型
     * @return 文件主键ID
     */
    @PostMapping("/file/file/record/upload")
    Result<Long> uploadFile(@RequestBody CreateFileRecord createFileRecord);

    /**
     * 获取文件详情
     *
     * @param dataId longId
     * @return FileRecord 类型
     */
    @GetMapping("/file/file/record/detail/{dataId}")
    Result<FileRecord> getFileDataByDataId(@PathVariable("dataId") Long dataId);


    /**
     * 获取文件详情 （包含数据）
     *
     * @param dataId long类型
     * @return FileRecordData 类型
     */
    @GetMapping("/file/file/record/data/{dataId}")
    Result<FileRecordData> getFileDataAndDataByDataId(@PathVariable("dataId") Long dataId);
}
