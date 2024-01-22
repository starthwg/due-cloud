package com.due.cloud.service.back.service.file;

import com.due.cloud.service.back.domian.response.FileRecordVo;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    Long uploadFile(MultipartFile multipartFile);

    /**
     * 获取文件详情
     *
     * @param dataId Long类型的
     * @return FileRecordVo 类信息
     */
    FileRecordVo getFileRecordByDataId(Long dataId);

    FileRecordVo getFileRecordAndDataByDataId(Long dataId);
}
