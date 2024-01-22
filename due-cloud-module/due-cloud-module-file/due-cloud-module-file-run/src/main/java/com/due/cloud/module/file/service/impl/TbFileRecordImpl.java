package com.due.cloud.module.file.service.impl;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.utils.FileUtil;
import com.due.cloud.bridge.file.domian.DuePutObjectCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectResult;
import com.due.cloud.bridge.file.support.FileTemplate;
import com.due.cloud.module.file.doamin.request.CreateFile;
import com.due.cloud.module.file.entity.TbFileRecord;
import com.due.cloud.module.file.mapper.TbFileRecordMapper;
import com.due.cloud.module.file.service.ITbFileRecordService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * <p>
 * 文件记录 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-19
 */
@Service
@Slf4j
public class TbFileRecordImpl extends TableDataServiceImpl<TbFileRecordMapper, TbFileRecord> implements ITbFileRecordService {

    @Autowired
    private FileTemplate fileTemplate;

    @Override
    public Long uploadFile(CreateFile createFile) {
        LogicAssert.isNull(createFile, ErrorEnum.PARAMETER_ERROR);
        LogicAssert.isBlank(createFile.getFileName(), ErrorEnum.PARAMETER_ERROR);
        LogicAssert.isNull(createFile.getFileData(), ErrorEnum.PARAMETER_ERROR);
        DuePutObjectCreate build = DuePutObjectCreate.builder().fileName(createFile.getFileName()).filePostfix(FileUtil.fileType(createFile.getFileName())).inputStream(new ByteArrayInputStream(createFile.getFileData())).newFileName(FileUtil.getFileName() + "." + FileUtil.fileType(createFile.getFileName())).build();
        DuePutObjectResult duePutObjectResult = null;
        try {
            duePutObjectResult = fileTemplate.putObject(build);
        } catch (IOException e) {
            throw new LogicException(ErrorEnum.DATA_HANDLE_ERROR, "文件上传异常");
        }
        LogicAssert.isNull(duePutObjectResult, ErrorEnum.DATA_HANDLE_ERROR, "文件上传失败");

        TbFileRecord tbFileRecord = new TbFileRecord();
        tbFileRecord.setFileDirect(duePutObjectResult.getClientEnum().name());
        tbFileRecord.setFilePath(duePutObjectResult.getFilePath());
        tbFileRecord.setFileSize(createFile.getFileSize());
        tbFileRecord.setFileRawName(createFile.getFileName());
        tbFileRecord.setFileNewName(duePutObjectResult.getNewFileName());
        tbFileRecord.setFileType(FileUtil.fileType(createFile.getFileName()));
        boolean updated = this.create(tbFileRecord);
        LogicAssert.isFalse(updated, ErrorEnum.DATA_HANDLE_ERROR, "文件上传失败！");
        return tbFileRecord.getDataId();
    }
}
