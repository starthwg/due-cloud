package com.due.cloud.service.back.controller.file;

import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.service.back.domian.response.FileRecordVo;
import com.due.cloud.service.back.service.file.IFileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController extends BasicController {


    private IFileService fileService;

    /**
     * 文件上传
     */
    @PostMapping("/uploadFile")
    public Result<Long> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return Result.exec(() -> this.fileService.uploadFile(multipartFile));
    }


    /**
     * 文件详情
     */
    @GetMapping("/detail/{dataId}")
    public Result<FileRecordVo> detail(@PathVariable("dataId") Long dataId) {
        return Result.exec(() -> this.fileService.getFileRecordByDataId(dataId));
    }

}
