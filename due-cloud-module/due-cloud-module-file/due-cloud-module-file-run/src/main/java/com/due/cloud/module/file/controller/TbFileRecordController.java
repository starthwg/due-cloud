package com.due.cloud.module.file.controller;


import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.module.file.doamin.request.CreateFileRecord;
import com.due.cloud.module.file.doamin.response.FileRecord;
import com.due.cloud.module.file.doamin.response.FileRecordData;
import com.due.cloud.module.file.service.ITbFileRecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文件记录 前端控制器
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-19
 */
@RestController
@RequestMapping("/file/record")
@AllArgsConstructor
public class TbFileRecordController extends BasicController {


    private final ITbFileRecordService fileRecordService;

    @PostMapping("/upload")
    public Result<Long> upload(@RequestBody CreateFileRecord createFileRecord) {
        return Result.exec(() -> this.fileRecordService.uploadFile(createFileRecord));
    }


    @GetMapping("/detail/{dataId}")
    public Result<FileRecord> detail(@PathVariable("dataId") Long dataId) {
        return Result.exec(() -> this.to( this.fileRecordService.selectDataByDataId(dataId),  FileRecord.class));
    }


    @GetMapping("/data/{dataId}")
    public Result<FileRecordData> data(@PathVariable("dataId") Long dataId) {
        return Result.exec(() -> this.fileRecordService.selectDataAndDataByDataId(dataId));
    }
}

