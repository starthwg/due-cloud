package com.due.cloud.service.back.service.file.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicException;
import com.due.cloud.module.file.doamin.request.CreateFile;
import com.due.cloud.service.back.integration.IFileIntegration;
import com.due.cloud.service.back.service.file.IFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;


@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements IFileService {


    private final IFileIntegration fileIntegration;

    @Override
    public Long uploadFile(MultipartFile multipartFile) {
        try {
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            CreateFile createFile = new CreateFile();
            createFile.setFileName(originalFilename);
            createFile.setFileData(bytes);
            createFile.setFileSize(new BigDecimal(multipartFile.getInputStream().available()));
            Result<Long> result = fileIntegration.uploadFile(createFile);
            result.whenFailureThenThrowException();
            return result.getData();
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new LogicException(ErrorEnum.DATA_HANDLE_ERROR);
        }
    }
}
