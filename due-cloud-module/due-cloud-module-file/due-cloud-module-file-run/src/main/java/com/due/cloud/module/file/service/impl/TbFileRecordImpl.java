package com.due.cloud.module.file.service.impl;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import com.due.basic.tookit.utils.FileUtil;
import com.due.cloud.bridge.file.domian.DueGetObjectCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectResult;
import com.due.cloud.bridge.file.domian.DuePutObjectCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectResult;
import com.due.cloud.bridge.file.support.FileTemplate;
import com.due.cloud.module.file.doamin.request.CreateFileRecord;
import com.due.cloud.module.file.doamin.response.FileRecordData;
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
    public Long uploadFile(CreateFileRecord createFileRecord) {
        LogicAssert.isNull(createFileRecord, ErrorEnum.PARAMETER_ERROR);
        LogicAssert.isBlank(createFileRecord.getFileName(), ErrorEnum.PARAMETER_ERROR);
        LogicAssert.isNull(createFileRecord.getFileData(), ErrorEnum.PARAMETER_ERROR);
        DuePutObjectCreate build = DuePutObjectCreate.builder().fileData(createFileRecord.getFileData()).fileName(createFileRecord.getFileName()).filePostfix(FileUtil.fileType(createFileRecord.getFileName())).inputStream(new ByteArrayInputStream(createFileRecord.getFileData())).newFileName(FileUtil.getFileName() + "." + FileUtil.fileType(createFileRecord.getFileName())).build();
        DuePutObjectResult duePutObjectResult = null;
        try {
            duePutObjectResult = fileTemplate.putObject(build);
        } catch (IOException e) {
            throw new LogicException(ErrorEnum.DATA_HANDLE_ERROR, "文件上传异常");
        }
        LogicAssert.isNull(duePutObjectResult, ErrorEnum.DATA_HANDLE_ERROR, "文件上传失败");

        TbFileRecord tbFileRecord = new TbFileRecord();
        tbFileRecord.setFileDirect(duePutObjectResult.getClientEnum());
        tbFileRecord.setFilePath(duePutObjectResult.getFilePath());
        tbFileRecord.setFileSize(createFileRecord.getFileSize());
        tbFileRecord.setFileRawName(createFileRecord.getFileName());
        tbFileRecord.setFileNewName(duePutObjectResult.getNewFileName());
        tbFileRecord.setFileType(FileUtil.fileType(createFileRecord.getFileName()));
        boolean updated = this.create(tbFileRecord);
        LogicAssert.isFalse(updated, ErrorEnum.DATA_HANDLE_ERROR, "文件上传失败！");
        return tbFileRecord.getDataId();
    }

    @Override
    public TbFileRecord selectDataByDataId(Long dataId) {
        LogicAssert.isNull(dataId, ErrorEnum.PARAMETER_ERROR);
        return this.selectById(dataId);
    }

    @Override
    public FileRecordData selectDataAndDataByDataId(Long dataId) {
        LogicAssert.isNull(dataId, ErrorEnum.PARAMETER_ERROR);
        TbFileRecord fileRecord = this.selectById(dataId);
        LogicAssert.isNull(fileRecord, ErrorEnum.DATA_ABSENT);

        FileRecordData result = this.copy(fileRecord, FileRecordData.class);

        // 获取文件数据
        DueGetObjectCreate objectCreate = DueGetObjectCreate.builder().filePath(fileRecord.getFilePath()).fileName(fileRecord.getFileNewName()).build();
        objectCreate.setClientEnum(fileRecord.getFileDirect());
        DueGetObjectResult objectResult = this.fileTemplate.getObject(objectCreate);
        LogicAssert.isNull(objectResult, ErrorEnum.DATA_ABSENT);

        result.setFileData(objectResult.getFileData());
        return result;
    }
}
