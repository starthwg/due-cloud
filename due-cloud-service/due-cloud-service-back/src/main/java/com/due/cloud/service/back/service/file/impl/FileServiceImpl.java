package com.due.cloud.service.back.service.file.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import com.due.bridge.tomcat.support.BasicService;
import com.due.cloud.module.file.doamin.request.CreateFileRecord;
import com.due.cloud.module.file.doamin.response.FileRecord;
import com.due.cloud.module.file.doamin.response.FileRecordData;
import com.due.cloud.service.back.domian.response.FileRecordVo;
import com.due.cloud.service.back.integration.IFileIntegration;
import com.due.cloud.service.back.service.file.IFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;


@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements IFileService, BasicService {


    private final IFileIntegration fileIntegration;

    @Override
    public Long uploadFile(MultipartFile multipartFile) {
        try {
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            CreateFileRecord createFileRecord = new CreateFileRecord();
            createFileRecord.setFileName(originalFilename);
            createFileRecord.setFileData(bytes);
            createFileRecord.setFileSize(new BigDecimal(multipartFile.getInputStream().available()));
            Result<Long> result = fileIntegration.uploadFile(createFileRecord);
            result.whenFailureThenThrowException();
            return result.getData();
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new LogicException(ErrorEnum.DATA_HANDLE_ERROR);
        }
    }

    @Override
    public FileRecordVo getFileRecordByDataId(Long dataId) {
        LogicAssert.isNull(dataId, ErrorEnum.PARAMETER_ERROR);

        Result<FileRecord> result = fileIntegration.getFileRecordByDataId(dataId);
        result.whenFailureThenThrowException();
        FileRecord fileRecord = result.getData();
        FileRecordVo vo = to(fileRecord, FileRecordVo.class);
        vo.setFileName(fileRecord.getFileRawName());
        return vo;
    }

    @Override
    public FileRecordVo getFileRecordAndDataByDataId(Long dataId) {
        LogicAssert.isNull(dataId, ErrorEnum.PARAMETER_ERROR);

        Result<FileRecordData> result = fileIntegration.getFileRecordAndDataByDataId(dataId);
        result.whenFailureThenThrowException();
        return to(result.getData(), FileRecordVo.class);
    }
}
