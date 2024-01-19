package com.due.cloud.module.file.controller;


import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.file.doamin.request.CreateFile;
import com.due.cloud.module.file.service.ITbFileRecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
public class TbFileRecordController {


    private final ITbFileRecordService fileRecordService;

    @PostMapping("/upload")
    public Result<Long> upload(@RequestBody CreateFile createFile) {
        return Result.exec(() -> this.fileRecordService.uploadFile(createFile));
    }
}

