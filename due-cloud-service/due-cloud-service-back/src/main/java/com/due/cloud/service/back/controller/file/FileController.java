package com.due.cloud.service.back.controller.file;

import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.service.back.service.file.IFileService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/uploadFile")
    public Result<Long> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return Result.exec(() -> this.fileService.uploadFile(multipartFile));
    }

}
